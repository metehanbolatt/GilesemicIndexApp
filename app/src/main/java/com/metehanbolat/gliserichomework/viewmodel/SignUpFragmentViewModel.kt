package com.metehanbolat.gliserichomework.viewmodel

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.metehanbolat.gliserichomework.view.activity.MainActivity

class SignUpFragmentViewModel : ViewModel() {

    val loadingControl = MutableLiveData<Boolean>()

    fun createUser(activity: Activity, view: View, firebase: Firebase, name:String, surname: String, phone: String, email: String, password: String) {
        val auth = firebase.auth
        loadingControl.value = true
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                sendUserDataToFirebase(firebase, name, surname, phone, email, password)
                Intent(activity.applicationContext, MainActivity::class.java).apply {
                    activity.startActivity(this)
                    activity.finish()
                }
            }else {
                loadingControl.value = false
                Snackbar.make(view, "An error occurred while registering.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendUserDataToFirebase(firebase: Firebase, name: String, surname: String, phone: String, email: String, password: String) {
        val database = firebase.firestore
        val userData = hashMapOf(
            "name" to name,
            "surname" to surname,
            "phone" to phone,
            "email" to email,
            "password" to password
        )
        database.collection(email).document("userData").set(userData)
    }
}