package com.mindorks.framework.mvvm.data.firebase

import android.content.Intent
import com.google.firebase.auth.FirebaseUser

interface FirebaseDataHelper {
    fun getSignInInent(): Intent
    fun getCurrentUser(): FirebaseUser?
}
