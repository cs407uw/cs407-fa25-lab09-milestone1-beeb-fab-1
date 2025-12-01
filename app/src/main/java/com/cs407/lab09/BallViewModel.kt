package com.cs407.lab09

import android.hardware.Sensor
import android.hardware.SensorEvent
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BallViewModel : ViewModel() {

    private var ball: Ball? = null
    private var lastTimestamp: Long = 0L
    private val ACC_SCALE = 80f
    private val _ballPosition = MutableStateFlow(Offset.Zero)
    val ballPosition: StateFlow<Offset> = _ballPosition.asStateFlow()

    /**
     * Called by the UI when the game field's size is known.
     */
    fun initBall(fieldWidth: Float, fieldHeight: Float, ballSizePx: Float) {
        android.util.Log.d("BallVM", "initBall called: $fieldWidth x $fieldHeight, ballSize=$ballSizePx")
        if (ball == null) {
            ball = Ball(
                backgroundWidth = fieldWidth,
                backgroundHeight = fieldHeight,
                ballSize = ballSizePx
            )

            ball!!.reset()

            _ballPosition.value = Offset(ball!!.posX, ball!!.posY)

            lastTimestamp = 0L
        }
    }

    /**
     * Called by the SensorEventListener in the UI.
     */
    fun onSensorDataChanged(event: SensorEvent) {
        android.util.Log.d("BallVM", "onSensorDataChanged called, type=${event.sensor.type}")
        val currentBall = ball ?: return

        if (event.sensor.type == Sensor.TYPE_GRAVITY) {
            if (lastTimestamp != 0L) {
                val NS2S = 1.0f / 1_000_000_000.0f
                val dT = (event.timestamp - lastTimestamp) * NS2S

                val rawX = event.values[0]
                val rawY = event.values[1]

                val xAcc = -rawX * ACC_SCALE
                val yAcc = -rawY * ACC_SCALE

                currentBall.updatePositionAndVelocity(
                    xAcc = xAcc,
                    yAcc = yAcc,
                    dT = dT
                )

                currentBall.checkBoundaries()

                _ballPosition.update { Offset(currentBall.posX, currentBall.posY) }
            }

            lastTimestamp = event.timestamp
        }
    }

    fun reset() {
        android.util.Log.d("BallVM", "reset called in ViewModel")
        // TODO: Reset the ball's state
         ball?.reset()

        // TODO: Update the StateFlow with the reset position
         ball?.let { _ballPosition.value = Offset(it.posX, it.posY) }

        // TODO: Reset the lastTimestamp
        lastTimestamp = 0L
    }
}