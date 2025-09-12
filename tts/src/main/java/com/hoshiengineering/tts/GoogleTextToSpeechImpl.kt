package com.hoshiengineering.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.*

class GoogleTextToSpeechImpl(context: Context) : AppTextToSpeech {
    private var tts: TextToSpeech? = null

    init {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.US
            }
        }
    }

    override fun speak(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun stop() {
        tts?.stop()
    }

    override fun release() {
        tts?.shutdown()
    }
}
