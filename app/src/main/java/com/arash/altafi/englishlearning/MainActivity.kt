package com.arash.altafi.englishlearning

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.arash.altafi.englishlearning.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var textToSpeechHelper: TextToSpeechHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            textToSpeechHelper = TextToSpeechHelper(this@MainActivity) {
                if (it)
                    loadingIndicator.visibility = View.GONE
                else
                    loadingIndicator.visibility = View.VISIBLE
            }

            btnSpeak.setOnClickListener {
                val text = etText.editText?.text.toString()
                textToSpeechHelper.speak(text)
            }

            btnStop.setOnClickListener {
                textToSpeechHelper.stop()
                loadingIndicator.visibility = View.GONE
            }

            sliderVolume.addOnChangeListener { _, value, fromUser ->
                if (fromUser) {
                    textToSpeechHelper.setupSound(value)
                }
            }

            sliderVolume.setLabelFormatter {
                it.toString()
            }

            sliderSpeed.addOnChangeListener { _, value, fromUser ->
                if (fromUser) {
                    textToSpeechHelper.setupSpeed(value)
                }
            }

            sliderSpeed.setLabelFormatter {
                it.toString()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeechHelper.shutdown()
    }
}