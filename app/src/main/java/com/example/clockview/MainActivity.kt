package com.example.clockview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private lateinit var mClockView: ClockView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         mClockView = findViewById(R.id.dialView)

    }

    override fun onStart() {
        super.onStart()
        mClockView.startClock()
    }

    override fun onStop() {
        super.onStop()
        mClockView.stopClock()
    }
}