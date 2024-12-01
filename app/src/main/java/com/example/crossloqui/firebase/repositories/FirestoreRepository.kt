package com.example.crossloqui.firebase.repositories

import android.icu.text.SimpleDateFormat
import android.util.Log
import com.example.crossloqui.data.FriendRequest
import com.example.crossloqui.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreRepository @Inject constructor(
    private val auth: FirebaseAuth,
    firestoreDB: FirebaseFirestore
) {
    fun hasUser(): Boolean = auth.currentUser != null

    fun getCurrentUserId(): String = auth.currentUser?.uid.orEmpty()

    private val requestRef: CollectionReference = firestoreDB.collection("friendRequest")
    private val currentUserRef: CollectionReference = firestoreDB.collection("users")
    private val targetUserRef: CollectionReference = firestoreDB.collection("users")

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
                    val response = if (snapshot != null){
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
            addTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        )
        requestRef
            .document(greetMessage.receiverId + getCurrentUserId())
            .set(greetMessage)
            .addOnCompleteListener {
                onComplete.invoke(it.isSuccessful)
            }
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