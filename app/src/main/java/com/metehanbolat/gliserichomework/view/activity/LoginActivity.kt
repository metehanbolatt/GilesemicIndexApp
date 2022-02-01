package com.metehanbolat.gliserichomework.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.metehanbolat.gliserichomework.R
import com.metehanbolat.gliserichomework.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setTheme(R.style.Theme_GlisericHomeWork)
        setContentView(view)
    }

    override fun onStart() {
        super.onStart()
    }
}