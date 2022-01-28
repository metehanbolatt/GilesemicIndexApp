package com.metehanbolat.gliserichomework.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.metehanbolat.gliserichomework.databinding.ActivityMainBinding
import com.metehanbolat.gliserichomework.utils.Constants.URL
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

@DelicateCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        GlobalScope.launch {
            getData()
        }
    }

    private fun getData() {
        val document = Jsoup.connect(URL).get()
        val element = document.select("tbody")
        for (tr in element.select("tr")){
            for (td in tr.select("td")){
                println(td.text())
            }
        }
    }
}