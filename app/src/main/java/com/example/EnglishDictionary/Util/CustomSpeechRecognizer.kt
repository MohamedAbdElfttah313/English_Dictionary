package com.example.EnglishDictionary.Util

import android.os.Bundle
import android.speech.RecognitionListener

abstract class CustomSpeechRecognizer() : RecognitionListener {

    override fun onBeginningOfSpeech() {
    }

    override fun onReadyForSpeech(p0: Bundle?) {
    }

    override fun onRmsChanged(p0: Float) {
    }

    override fun onBufferReceived(p0: ByteArray?) {
    }

    override fun onEndOfSpeech() {
    }

    override fun onError(p0: Int) {

    }

    override fun onPartialResults(p0: Bundle?) {
    }

    override fun onEvent(p0: Int, p1: Bundle?) {
    }
}