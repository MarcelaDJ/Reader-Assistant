package com.hoshiengineering.readerassistant

import android.app.Application
import android.text.TextUtils
import android.util.Log
import com.hoshiengineering.core.utils.ServiceAvailabilityProvider
import com.huawei.agconnect.AGConnectOptionsBuilder
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.hms.common.ApiException
import com.huawei.hms.mlsdk.common.MLApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyReaderAssistant : Application() {
    override fun onCreate() {
        super.onCreate()

        val isHmsAvailable = ServiceAvailabilityProvider.isHmsAvailable(this)

        if (isHmsAvailable) {
            MLApplication.initialize(applicationContext)
            val ttsInit = MLApplication.getInstance()
                .setApiKey("DgEDAMtrN9E3+LJdxEABVC9Y5FiYMHnxldAwlfn1h25cPWE9ccU3vxwG+0F/8toxqItHYPPdjBYyHIBDrwesnF8ZbO17ZIhL1NGRsA==")
            Log.d("HuaweiTTS", "HMS TTS inicializado con appId: $ttsInit")
            //  token
        } else {
            /* FirebaseMessaging.getInstance().token.addOnSuccessListener { result ->
              LogTaxisLibres.i(TAG, "FCM token: $result")
              tokenFcm = result
          }*/
        }
    }

    /* get hms token from huawei */
    private val token: Unit
        get() {
            object : Thread() {
                override fun run() {
                    try {
                        // read from agconnect-services.json
                        val appId = AGConnectOptionsBuilder().build(applicationContext)
                            .getString("client/app_id")
                        val token =
                            HmsInstanceId.getInstance(applicationContext).getToken(appId, "HCM")

                        if (!TextUtils.isEmpty(token)) {
                            Log.i("TAG", "HMS token: $token")
                            //  tokenHcm = token
                        }
                    } catch (e: ApiException) {
                        Log.e("TAG", "get token failed, $e")
                    }
                }
            }.start()
        }

}