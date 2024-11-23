package com.example.crossloqui.firebase.repositories

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class FirestoreRepository {
    val firestoreDB = FirebaseFirestore.getInstance()

    suspend fun getNewFriendList(currentUserId: String): Task<QuerySnapshot> {
        return firestoreDB.collection("friendRequest")
            .whereArrayContains("members", currentUserId)
            .get()
    }
}