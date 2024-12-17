package com.example.crossloqui.firebase.repositories

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.example.crossloqui.data.FriendRequest
import com.example.crossloqui.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObjects
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreRepository @Inject constructor(
    private val auth: FirebaseAuth,
    firestoreDB: FirebaseFirestore,
    firebaseStorage: FirebaseStorage,
    @ApplicationContext private val context: Context
) {
    fun hasUser(): Boolean = auth.currentUser != null

    fun getCurrentUserId(): String = auth.currentUser?.uid.orEmpty()

    private val requestRef: CollectionReference = firestoreDB.collection("friendRequest")
    private val currentUserRef: CollectionReference = firestoreDB.collection("users")
    private val targetUserRef: CollectionReference = firestoreDB.collection("users")
    private val storageRef: StorageReference = firebaseStorage.reference


    fun createUser(
        userName: String,
        email: String,
        bio: String,
        gender: String,
        birthday: String,
        registerDate: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")),
        password: String,
        imagePath: String,
        navigateToHomepage: () -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val file = Uri.fromFile(File(imagePath))
                    val avatarRef = storageRef.child("avatars/${getCurrentUserId()}")
                    val uploadTask = avatarRef.putFile(file)
                    uploadTask.addOnFailureListener {
                        Toast.makeText(
                            context,
                            "upload failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }.addOnSuccessListener {
                        Toast.makeText(
                            context,
                            "Registration successful",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    val user = User(
                        name = userName,
                        email = email,
                        bio = bio,
                        id = getCurrentUserId(),
                        gender = gender,
                        birthday = birthday,
                        registerDate = registerDate,
                        followingCount = 0,
                        followerCount = 0
                    )
                    currentUserRef.add(user)
                    navigateToHomepage()
                } else {
                    val e = task.exception
                    if (e is FirebaseAuthUserCollisionException) {
                        Toast.makeText(
                            context,
                            "Email address already registered",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            "Registration failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }

    fun getNewFriendList(currentId: String): Flow<Resources<List<FriendRequest>>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null

        try {
            snapshotStateListener = requestRef
                .whereArrayContains("members", currentId)
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        val friendRequest = snapshot.toObjects(FriendRequest::class.java).sortedByDescending {
                            SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(it.addTime)
                        }
                        Resources.Success(data = friendRequest)
                    } else {
                        Resources.Error(throwable = e?.cause)
                    }
                    trySend(response)
                }
        } catch (e: Exception) {
            trySend(Resources.Error(e.cause))
        }

        awaitClose {
            snapshotStateListener?.remove()
        }
    }

    fun getTargetUser(targetUserEmail: String): Flow<Resources<User>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null
        Log.d("checkLocation", "Location1")

        try {
            Log.d("checkLocation", "Location2")
            snapshotStateListener = targetUserRef
                .whereEqualTo("email", targetUserEmail)
                .addSnapshotListener { snapshot, e ->
                    Log.d("checkLocation", "Location3")
                    val response = if (snapshot?.isEmpty == false){
                        val targetUser = snapshot.toObjects<User>()
                        Log.d("checkLocation", "success search")
                        Log.d("checkLocation", targetUser[0].email)
                        Resources.Success(data = targetUser[0])
                    } else {
                        Log.d("checkLocation", "Location4")
                        Resources.Error(throwable = e?.cause)
                    }
                    trySend(response)
                }
        } catch (e: Exception) {
            Log.d("checkLocation", e.message.toString())
            trySend(Resources.Error(e.cause))
        }

        awaitClose {
            snapshotStateListener?.remove()
        }
    }

    fun getCurrentUser(): Flow<Resources<User>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null

        try {
            snapshotStateListener = currentUserRef
                .whereEqualTo("id", getCurrentUserId())
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        val currentUser = snapshot.toObjects<User>()[0]
                        Resources.Success(data = currentUser)
                    } else {
                        Resources.Error(throwable = e?.cause)
                    }
                    trySend(response)
                }
        } catch (e: Exception) {
            trySend(Resources.Error(e.cause))
        }

        awaitClose {
            snapshotStateListener?.remove()
        }
    }

    fun createFriendRequest(
        senderName: String,
        receiverId: String,
        receiverName: String,
        message: String,
        onComplete: (Boolean) -> Unit
    ) {
        val greetMessage = FriendRequest(
            senderId = getCurrentUserId(),
            senderName = senderName,
            receiverId = receiverId,
            receiverName = receiverName,
            members = mutableListOf(getCurrentUserId(), receiverId),
            message = message,
            addTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            requestStatus = "progressing"
        )
        requestRef
            .document(greetMessage.receiverId + getCurrentUserId())
            .set(greetMessage)
            .addOnCompleteListener {
                onComplete.invoke(it.isSuccessful)
            }
    }

    fun friendRequestPass(
        receiverId: String,
        senderId: String
    ) {
        currentUserRef
            .whereEqualTo("id", getCurrentUserId())
            .get()
            .addOnSuccessListener { documents ->
                val document = documents.documents[0]
                val docRef = document.reference

                docRef.update("friendList", FieldValue.arrayUnion(senderId))
            }

        requestRef.document(receiverId + senderId).update(
            "requestStatus", "accepted"
        )
    }
}


sealed class Resources<T>(
    val data: T? = null,
    val throwable: Throwable? = null
) {
    class Loading<T>: Resources<T>()
    class Success<T>(data: T?): Resources<T>(data = data)
    class Error<T>(throwable: Throwable?): Resources<T>(throwable = throwable)
}