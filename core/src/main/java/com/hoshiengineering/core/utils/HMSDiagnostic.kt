package com.hoshiengineering.core

import android.content.Context
import android.util.Log
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.hms.api.HuaweiApiAvailability

class HMSDiagnostic {
    
    companion object {
        fun diagnoseHmsSetup(context: Context) {
            try {
                Log.d("HMS_DIAGNOSTIC", "=== INICIANDO DIAGNÓSTICO HMS ===")
                
                // 1. Verificar disponibilidad de HMS Core
                val availability = HuaweiApiAvailability.getInstance()
                val resultCode = availability.isHuaweiMobileServicesAvailable(context)
                
                Log.d("HMS_DIAGNOSTIC", "HMS Core disponible: $resultCode")
                Log.d("HMS_DIAGNOSTIC", "HMS Core mensaje: ${getHmsAvailabilityMessage(resultCode)}")
                
                // 2. Verificar configuración de AGConnect
                try {
                    val config = AGConnectServicesConfig.fromContext(context)
                    val appId = config.getString("client/app_id")
                    val packageName = config.getString("client/package_name")
                    
                    Log.d("HMS_DIAGNOSTIC", "App ID desde config: $appId")
                    Log.d("HMS_DIAGNOSTIC", "Package name desde config: $packageName")
                    Log.d("HMS_DIAGNOSTIC", "Package name real: ${context.packageName}")
                    
                } catch (e: Exception) {
                    Log.e("HMS_DIAGNOSTIC", "Error leyendo configuración AGConnect: ${e.message}")
                }
                
                // 3. Verificar archivo agconnect-services.json
                checkAgcFile(context)
                
                Log.d("HMS_DIAGNOSTIC", "=== FIN DIAGNÓSTICO ===")
                
            } catch (e: Exception) {
                Log.e("HMS_DIAGNOSTIC", "Error en diagnóstico: ${e.message}")
            }
        }
        
        private fun getHmsAvailabilityMessage(resultCode: Int): String {
            return when (resultCode) {
                com.huawei.hms.api.ConnectionResult.SUCCESS -> "SUCCESS"
                com.huawei.hms.api.ConnectionResult.SERVICE_MISSING -> "SERVICE_MISSING"
                com.huawei.hms.api.ConnectionResult.SERVICE_UPDATING -> "SERVICE_UPDATING"
                com.huawei.hms.api.ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED -> "SERVICE_VERSION_UPDATE_REQUIRED"
                com.huawei.hms.api.ConnectionResult.SERVICE_DISABLED -> "SERVICE_DISABLED"
                com.huawei.hms.api.ConnectionResult.SERVICE_INVALID -> "SERVICE_INVALID"
                else -> "UNKNOWN_ERROR: $resultCode"
            }
        }
        
        private fun checkAgcFile(context: Context) {
            try {
                val resources = context.resources
                val resourceId = resources.getIdentifier("agconnect-services", "raw", context.packageName)
                
                if (resourceId != 0) {
                    Log.d("HMS_DIAGNOSTIC", "agconnect-services.json encontrado en recursos")
                } else {
                    Log.e("HMS_DIAGNOSTIC", "agconnect-services.json NO encontrado en recursos")
                }
            } catch (e: Exception) {
                Log.e("HMS_DIAGNOSTIC", "Error buscando agconnect-services.json: ${e.message}")
            }
        }
    }
}