package com.arash.altafi.englishlearning

import android.content.Context
import android.media.AudioManager
import android.speech.tts.TextToSpeech
import java.util.*

class TextToSpeechHelper(
    private val context: Context,
    private val isSuccess: ((Boolean) -> Unit)? = null
) : TextToSpeech.OnInitListener {

    private var textToSpeech: TextToSpeech? = null

    init {
        textToSpeech = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech?.setLanguage(Locale.ENGLISH)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Language not supported, handle the error
                isSuccess?.invoke(false)
            }
            isSuccess?.invoke(true)
        } else {
            // Initialization failed, handle the error
            isSuccess?.invoke(false)
        }
    }

    fun setupSound(sound: Float) {
        textToSpeech?.setPitch(sound)
    }

    fun setupSpeed(speechRate: Float) {
        textToSpeech?.setSpeechRate(speechRate)
    }

    fun speak(text: String) {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.setStreamVolume(
            AudioManager.STREAM_MUSIC,
            audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
            0
        )
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun stop() {
        textToSpeech?.stop()
    }

    fun shutdown() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
    }
}