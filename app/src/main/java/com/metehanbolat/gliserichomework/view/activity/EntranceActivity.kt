package com.metehanbolat.gliserichomework.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.metehanbolat.gliserichomework.R
import com.metehanbolat.gliserichomework.databinding.ActivityEntranceBinding

class EntranceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEntranceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntranceBinding.inflate(layoutInflater)
        val view = binding.root
        setTheme(R.style.Theme_GlisericHomeWork)
        setContentView(view)

        object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {}
            override fun onFinish() {
                Intent(this@EntranceActivity, LoginActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            }
        }.start()
    }
}