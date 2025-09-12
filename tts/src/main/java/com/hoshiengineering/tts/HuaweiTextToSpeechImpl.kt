package com.hoshiengineering.tts

import android.content.Context
import com.huawei.hms.mlsdk.tts.MLTtsConfig
import com.huawei.hms.mlsdk.tts.MLTtsEngine
import com.huawei.hms.mlsdk.tts.MLTtsConstants

class HuaweiTextToSpeechImpl(context: Context) : AppTextToSpeech {
    private var engine: MLTtsEngine

    init {
        val config = MLTtsConfig()
            .setLanguage(MLTtsConstants.TTS_EN_US)
            .setPerson(MLTtsConstants.TTS_SPEAKER_FEMALE_EN)
            .setSpeed(1.0f)
            .setVolume(1.0f)
        engine = MLTtsEngine(config)
    }

    override fun speak(text: String) {
        engine.speak(text, MLTtsEngine.QUEUE_FLUSH)
    }

    override fun stop() {
        engine.stop()
    }

    override fun release() {
        engine.shutdown()
    }
}
