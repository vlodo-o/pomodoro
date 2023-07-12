package com.vlodo.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    var timerState: Boolean = false
    lateinit var timerTextView: TextView
    lateinit var startButton: FloatingActionButton

    val timer = PomodoroTimer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setListeners()

        timer.timerState.observe(this) { state ->
            timerState = state
            if (timerState) {
                startButton.setImageResource(R.drawable.ic_pause)
            }
            else {
                startButton.setImageResource(R.drawable.ic_play)
            }
        }

        timer.timerProgress.observe(this) { time ->
            timerTextView.text = time
        }
    }

    private fun initViews() {
        timerTextView = findViewById(R.id.timer_textview)
        startButton = findViewById(R.id.start_button)
    }

    private fun setListeners() {
        startButton.setOnClickListener {
            if (timerState) {
                timer.pauseTimer()
            }
            else {
                timer.startTimer()
            }
        }
    }

}