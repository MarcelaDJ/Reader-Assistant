package com.hoshiengineering.core.utils

import android.content.Context
import com.google.android.gms.common.ConnectionResult as GmsConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.huawei.hms.api.ConnectionResult as HmsConnectionResult
import com.huawei.hms.api.HuaweiApiAvailability

object ServiceAvailabilityProvider {

    fun isHmsAvailable(context: Context): Boolean {
        val availability = HuaweiApiAvailability.getInstance()
            .isHuaweiMobileServicesAvailable(context)

        return when (availability) {
            HmsConnectionResult.SUCCESS -> true
            HmsConnectionResult.SERVICE_MISSING,
            HmsConnectionResult.SERVICE_DISABLED,
            HmsConnectionResult.SERVICE_INVALID -> false
            else -> false
        }
    }

    fun isGmsAvailable(context: Context): Boolean {
        val availability = GoogleApiAvailability.getInstance()
            .isGooglePlayServicesAvailable(context)

        return when (availability) {
            GmsConnectionResult.SUCCESS -> true
            GmsConnectionResult.SERVICE_MISSING,
            GmsConnectionResult.SERVICE_DISABLED,
            GmsConnectionResult.SERVICE_INVALID -> false
            else -> false
        }
    }

    fun getAvailableService(context: Context): ServiceType {
        return when {
            isHmsAvailable(context) -> ServiceType.HMS
            isGmsAvailable(context) -> ServiceType.GMS
            else -> ServiceType.NONE
        }
    }
}

enum class ServiceType {
    HMS,
    GMS,
    NONE
}
