package com.metehanbolat.gliserichomework.utils

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.notifyObserver() {
    this.postValue(this.value)
}

fun View?.removeSelf() {
    this ?: return
    val parentView = parent as? ViewGroup ?: return
    parentView.removeView(this)
}
