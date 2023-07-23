package com.vlodo.pomodoro

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.text.SimpleDateFormat
import java.util.*

class PomodoroTimer {

    private var millisecondsLeft: Long = timerDuration
    private val mainThreadHandler = Handler(Looper.getMainLooper())
    private var runnable = getRunnable()

    private val _timerState = MutableLiveData<TimerState>()
    val timerState: LiveData<TimerState> = _timerState

    private val _timerProgress = MutableLiveData<String>()
    val timerProgress: LiveData<String> = _timerProgress


    fun getRunnable(): Runnable {
        return object: Runnable {
            override fun run() {
                if (millisecondsLeft > 1000) {
                    millisecondsLeft -= 1000
                    _timerProgress.value = millisecondsToString(millisecondsLeft)
                    mainThreadHandler.postDelayed(this, DURATION_UPDATE_DELAY_MS)
                }
                else {
                    _timerState.value = TimerState.ENDED
                    stopTimer()
                }
            }
        }
    }

    fun startTimer() {
        mainThreadHandler.postDelayed(runnable, DURATION_UPDATE_DELAY_MS)
        _timerState.value = TimerState.RUNNING
    }

    fun pauseTimer() {
        mainThreadHandler.removeCallbacks(runnable)
        _timerState.value = TimerState.PAUSE
    }

    fun stopTimer() {
        mainThreadHandler.removeCallbacks(runnable)
        millisecondsLeft = timerDuration
        _timerProgress.value = millisecondsToString(millisecondsLeft)
        _timerState.value = TimerState.PAUSE
    }

    fun millisecondsToString(duration: Long): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(duration)
    }

    companion object {
        private const val DURATION_UPDATE_DELAY_MS = 1000L
        var timerDuration = 1500000L
    }
}