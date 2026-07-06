package com.example.stopwatchapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var timerText: TextView
    private lateinit var startBtn: Button
    private lateinit var stopBtn: Button
    private lateinit var resetBtn: Button

    private var seconds = 0
    private var running = false

    private val handler = Handler(Looper.getMainLooper())

    private val runnable = object : Runnable {
        override fun run() {

            val hours = seconds / 3600
            val minutes = (seconds % 3600) / 60
            val secs = seconds % 60

            val time = String.format("%02d:%02d:%02d", hours, minutes, secs)
            timerText.text = time

            if (running) {
                seconds++
            }

            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timerText = findViewById(R.id.timerText)
        startBtn = findViewById(R.id.startBtn)
        stopBtn = findViewById(R.id.stopBtn)
        resetBtn = findViewById(R.id.resetBtn)

        handler.post(runnable)

        startBtn.setOnClickListener {
            running = true
            startBtn.isEnabled = false
            stopBtn.isEnabled = true
        }

        stopBtn.setOnClickListener {
            running = false
            startBtn.isEnabled = true
            stopBtn.isEnabled = false
        }

        resetBtn.setOnClickListener {
            running = false
            seconds = 0
            timerText.text = "00:00:00"

            startBtn.isEnabled = true
            stopBtn.isEnabled = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}