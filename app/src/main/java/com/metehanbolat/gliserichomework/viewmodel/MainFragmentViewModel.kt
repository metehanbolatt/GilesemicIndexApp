package com.metehanbolat.gliserichomework.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metehanbolat.gliserichomework.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.lang.Exception

class MainFragmentViewModel : ViewModel() {

    private val _firstDataList = MutableLiveData<ArrayList<String>>()
    val firstDataList : LiveData<ArrayList<String>>
        get() = _firstDataList

    init {
        _firstDataList.value = arrayListOf("")
    }

    suspend fun getData(document: Document) {
        withContext(Dispatchers.Main) {
            _firstDataList.value = arrayListOf()
            val element = document.select("tbody")
            for (tr in element.select("tr")){
                for (td in tr.select("td")){
                    _firstDataList.value?.add(td.text())
                    _firstDataList.notifyObserver()
                }
            }
        }
    }

}


fun <T> MutableLiveData<T>.notifyObserver() {
    this.postValue(this.value)
}