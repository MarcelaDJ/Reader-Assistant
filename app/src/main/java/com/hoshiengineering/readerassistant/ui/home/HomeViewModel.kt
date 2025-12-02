package com.hoshiengineering.readerassistant.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _editTextContent = MutableLiveData<String>()
    val editTextContent: LiveData<String> = _editTextContent

    fun setEditTextContent(text: String) {
        _editTextContent.value = text
    }
}