package com.hoshiengineering.tts

interface AppTextToSpeech {
    fun speak(text: String)
    fun stop()
    fun release()
}