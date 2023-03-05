package com.example.clockview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private lateinit var mClockView1: ClockView
    private lateinit var mClockView2: ClockView
    private lateinit var mClockView3: ClockView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         mClockView1 = findViewById(R.id.ClockView_1)
        setContentView(R.layout.activity_main)
        mClockView2 = findViewById(R.id.ClockView_2)
        setContentView(R.layout.activity_main)
        mClockView3 = findViewById(R.id.ClockView_3)


    }

    override fun onStart() {
        super.onStart()
        mClockView1.startClock()
        mClockView2.startClock()
        mClockView3.startClock()
    }

    override fun onStop() {
        super.onStop()
        mClockView1.stopClock()
        mClockView2.stopClock()
        mClockView3.stopClock()
    }
}