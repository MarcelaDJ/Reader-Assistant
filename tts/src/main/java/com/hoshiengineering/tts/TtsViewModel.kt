package com.hoshiengineering.tts

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TtsViewModel @Inject constructor(
    private val tts: AppTextToSpeech
) : ViewModel() {

    fun speak(text: String) {
        tts.speak(text)
    }

    override fun onCleared() {
        super.onCleared()
        tts.release()
    }
}