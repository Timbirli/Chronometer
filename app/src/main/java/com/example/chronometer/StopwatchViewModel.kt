package com.example.chronometer

import androidx.lifecycle.ViewModel

class StopwatchViewModel : ViewModel() {
    var startTime: Long = 0L

    var elapsedTime: Long = 0L

    var isRunning: Boolean = false
}