package com.example.crossloqui.firebase.repositories

import android.icu.text.SimpleDateFormat
import com.example.crossloqui.data.FriendRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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
}


sealed class Resources<T>(
    val data: T? = null,
    val throwable: Throwable? = null
) {
    class Loading<T>: Resources<T>()
    class Success<T>(data: T?): Resources<T>(data = data)
    class Error<T>(throwable: Throwable?): Resources<T>(throwable = throwable)
}