package com.metehanbolat.gliserichomework.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.metehanbolat.gliserichomework.R
import com.metehanbolat.gliserichomework.databinding.ActivityEntranceBinding

class EntranceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEntranceBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntranceBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth

        object : CountDownTimer(2400, 1000) {
            override fun onTick(p0: Long) {}
            override fun onFinish() {
                val currentUser = auth.currentUser
                if (currentUser == null) {
                    Intent(this@EntranceActivity, LoginActivity::class.java).apply {
                        startActivity(this)
                        finish()
                    }
                }else {
                    Intent(this@EntranceActivity, MainActivity::class.java).apply {
                        startActivity(this)
                        finish()
                    }
                }
            }
        }.start()
    }
}