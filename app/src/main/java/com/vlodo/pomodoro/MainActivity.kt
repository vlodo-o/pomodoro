package com.vlodo.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    var timerState: TimerState = TimerState.PAUSE
    lateinit var timerTextView: TextView
    lateinit var startButton: FloatingActionButton

    private val timer = PomodoroTimer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setListeners()

        timer.timerState.observe(this) { state ->
            timerState = state
            when (state) {
                TimerState.PAUSE -> {
                    startButton.setImageResource(R.drawable.ic_play)
                }
                TimerState.RUNNING -> {
                    startButton.setImageResource(R.drawable.ic_pause)
                }
                TimerState.ENDED -> {
                    startButton.setImageResource(R.drawable.ic_play)
                    Toast.makeText(this, "Время работы закончено, пора сделать перерыв!", Toast.LENGTH_SHORT).show()
                }
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
            if (timerState == TimerState.RUNNING) {
                timer.pauseTimer()
            }
            else {
                timer.startTimer()
            }
        }
    }

}