package com.hoshiengineering.tts

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Pair
import com.huawei.hms.mlsdk.model.download.MLLocalModelManager
import com.huawei.hms.mlsdk.tts.MLTtsAudioFragment
import com.huawei.hms.mlsdk.tts.MLTtsCallback
import com.huawei.hms.mlsdk.tts.MLTtsConfig
import com.huawei.hms.mlsdk.tts.MLTtsConstants
import com.huawei.hms.mlsdk.tts.MLTtsEngine
import com.huawei.hms.mlsdk.tts.MLTtsError
import com.huawei.hms.mlsdk.tts.MLTtsLocalModel
import com.huawei.hms.mlsdk.tts.MLTtsWarn


class HuaweiTextToSpeechImpl(context: Context) : AppTextToSpeech {
    private var engine: MLTtsEngine

    init {
        val config = MLTtsConfig()
            .setLanguage(MLTtsConstants.TTS_EN_US)
            .setPerson(MLTtsConstants.TTS_SPEAKER_FEMALE_EN)
            .setSpeed(1.0f)
            .setVolume(10.0f)
        engine = MLTtsEngine(config)
        engine.updateConfig(config)

        engine.setTtsCallback(object : MLTtsCallback {

            override fun onError(p0: String?, p1: MLTtsError?) {
                Log.e("HuaweiTTS", "Error $p0: $p1")

            }

            override fun onWarn(p0: String?, p1: MLTtsWarn?) {
               Log.e("HuaweiTTS", "warn $p0: $p1")
            }

            override fun onRangeStart(p0: String?, p1: Int, p2: Int) {
                Log.e("HuaweiTTS", "rangestart $p0: $p1")
            }

            override fun onAudioAvailable(
                p0: String?,
                p1: MLTtsAudioFragment?,
                p2: Int,
                p3: Pair<Int?, Int?>?,
                p4: Bundle?
            ) {
                Log.d("HuaweiTTS", "Audio generado, tamaño=${p0}")
            }

            override fun onEvent(p0: String?, p1: Int, p2: Bundle?) {
                Log.e("HuaweiTTS", "Event $p0: $p1")
            }
        })
    }

    override fun speak(text: String) {
        Log.d("HuaweiTTS", "speak() called with text: $text")
        try {
            val result = engine.speak(text, MLTtsEngine.QUEUE_FLUSH)
            Log.d("HuaweiTTS", "speak result: $result")
        } catch (e: Exception) {
            Log.e("HuaweiTTS", "speak failed", e)
        }
    }

    override fun stop() {
        engine.stop()
    }

    override fun release() {
        engine.shutdown()
    }
}
