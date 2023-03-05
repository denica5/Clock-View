package com.example.clockview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private lateinit var mClockView1: ClockView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         mClockView1 = findViewById(R.id.clock_view_1)
        setContentView(R.layout.activity_main)



    }

    override fun onStart() {
        super.onStart()
        mClockView1.startClock()

    }

    override fun onStop() {
        super.onStop()
        mClockView1.stopClock()

    }
}