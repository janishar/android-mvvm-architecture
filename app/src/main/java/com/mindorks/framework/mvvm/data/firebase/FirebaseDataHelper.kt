package com.mindorks.framework.mvvm.data.firebase

import android.content.Intent
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference

interface FirebaseDataHelper {
    fun getSignInInent(): Intent
    fun getCurrentUser(): FirebaseUser?
    fun setUserInFirestore(userMap: Map<String, Any>): Task<DocumentReference>
}
