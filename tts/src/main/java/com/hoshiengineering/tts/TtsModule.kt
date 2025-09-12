package com.hoshiengineering.tts

import android.content.Context
import com.hoshiengineering.core.utils.ServiceAvailabilityProvider

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TtsModule {

    @Provides
    @Singleton
    fun provideAppTextToSpeech(@ApplicationContext context: Context): AppTextToSpeech {
        val isHmsAvailable = ServiceAvailabilityProvider.isHmsAvailable(context)

        return if (isHmsAvailable) {
            HuaweiTextToSpeechImpl(context)
        } else {
            GoogleTextToSpeechImpl(context)
        }
    }
}
