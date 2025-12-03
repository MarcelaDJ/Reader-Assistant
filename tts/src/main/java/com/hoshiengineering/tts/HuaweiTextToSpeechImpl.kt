package com.hoshiengineering.tts

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.widget.Toast
import com.huawei.hms.mlsdk.tts.MLTtsAudioFragment
import com.huawei.hms.mlsdk.tts.MLTtsCallback
import com.huawei.hms.mlsdk.tts.MLTtsConfig
import com.huawei.hms.mlsdk.tts.MLTtsConstants
import com.huawei.hms.mlsdk.tts.MLTtsEngine
import com.huawei.hms.mlsdk.tts.MLTtsError
import com.huawei.hms.mlsdk.tts.MLTtsWarn
import java.util.Locale


class HuaweiTextToSpeechImpl(context: Context) : AppTextToSpeech {
    private var engine: MLTtsEngine?= null
    val cont :Context= context

    init {
        initializeEngine()
    }
    private fun initializeEngine() {
        val ttsConf= getHmsTtsLanguage(cont)
        val config = MLTtsConfig()
            .setLanguage(ttsConf.language)
            .setPerson(ttsConf.person)
            .setSpeed(1.0f)
            .setVolume(10.0f)

        engine = MLTtsEngine(config)
        engine?.setTtsCallback(object : MLTtsCallback {
            // (Tus implementaciones de callbacks aquí)
            override fun onError(p0: String?, p1: MLTtsError) {
                Log.e("HuaweiTTS", "Error $p0: $p1")
            }

            override fun onWarn(p0: String?, p1: MLTtsWarn) {
                Log.e("HuaweiTTS", "warn $p0: $p1")
            }

            override fun onRangeStart(p0: String, p1: Int, p2: Int) {
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

                if (p1 == MLTtsConstants.EVENT_PLAY_STOP) {
                    Log.e("HuaweiTTS",  "Service Stopped")
                }
            }
        })
    }

    override fun speak(text: String) {
        release() // Llama a shutdown()
        initializeEngine() // Crea un motor nuevo y listo para usar
        Log.d("HuaweiTTS", "speak() called with text: $text")
        try {
            val result = engine?.speak(text, MLTtsEngine.QUEUE_FLUSH)
            Log.d("HuaweiTTS", "speak result: $result")
        } catch (e: Exception) {
            Log.e("HuaweiTTS", "speak failed", e)
        }
    }

    override fun stop() {
        engine?.stop()
    }

    override fun release() {
        engine?.shutdown()
        engine = null
    }

    private fun getHmsTtsLanguage(context: Context): HmsTtsConfig {

        val locale = Locale.getDefault()
        val languageCode = locale.language.lowercase(Locale.ROOT) // Ej: "es", "en"

        return when (languageCode) {
            "es" -> {
                HmsTtsConfig(
                    language =MLTtsConstants.TTS_LAN_ES_ES,
                    person = MLTtsConstants.TTS_SPEAKER_FEMALE_ES
                )
            }

            "en" -> {
                HmsTtsConfig(
                MLTtsConstants.TTS_EN_US,
                    MLTtsConstants.TTS_SPEAKER_FEMALE_EN_1
                )
            }

            else -> {
                HmsTtsConfig(
                    MLTtsConstants.TTS_EN_US,
                    MLTtsConstants.TTS_SPEAKER_FEMALE_EN_1
                )
            }
        }
    }

    data class HmsTtsConfig(
        val language: String,
        val person: String
    )
}
