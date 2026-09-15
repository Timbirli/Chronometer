package com.example.chronometer

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var tvTimer: TextView
    private lateinit var btnStart: Button
    private lateinit var btnPause: Button
    private lateinit var btnReset: Button

    private val viewModel: StopwatchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTimer = findViewById(R.id.tvTimer)
        btnStart = findViewById(R.id.btnStart)
        btnPause = findViewById(R.id.btnPause)
        btnReset = findViewById(R.id.btnReset)

    }

    // Форматирование из миллисекунд в строковый формат
    private fun formatTime(millis: Long): String {
        val seconds = (millis / 1000) % 60
        val minutes = (millis / (1000 * 60)) % 60
        val hours = (millis / (1000 * 60 * 60))

        return if (hours > 0) {
            String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        }
    }

    // Handler для обновления
    private val handler = android.os.Handler(android.os.Looper.getMainLooper())

    private val updateRunnable = object : Runnable {
        override fun run() {
            updateTimerText()
            handler.postDelayed(this, 1000) // Повторить через 1 секунду
        }
    }

    private fun getCurrentElapsedTime(): Long {
        return if (viewModel.isRunning) {
            viewModel.elapsedTime + (android.os.SystemClock.elapsedRealtime() - viewModel.startTime)
        } else {
            viewModel.elapsedTime
        }
    }

    private fun updateTimerText() {
        val elapsedMillis = getCurrentElapsedTime()
        tvTimer.text = formatTime(elapsedMillis)
    }

    private fun startTimerUpdates() {
        handler.removeCallbacks(updateRunnable)
        handler.post(updateRunnable)
    }

    private fun stopTimerUpdates() {
        handler.removeCallbacks(updateRunnable)
    }
}