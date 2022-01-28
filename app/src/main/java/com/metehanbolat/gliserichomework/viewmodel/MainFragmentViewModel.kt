package com.metehanbolat.gliserichomework.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.nodes.Document

class MainFragmentViewModel : ViewModel() {

    val firstDataList = MutableLiveData<ArrayList<String>>()

    suspend fun getData(document: Document) {
        withContext(Dispatchers.Main) {
            firstDataList.value = arrayListOf()
            val element = document.select("tbody")
            for (tr in element.select("tr")){
                for (td in tr.select("td")){
                    firstDataList.value?.add(td.text())
                    firstDataList.notifyObserver()
                }
            }
        }
    }

}

fun <T> MutableLiveData<T>.notifyObserver() {
    this.postValue(this.value)
}