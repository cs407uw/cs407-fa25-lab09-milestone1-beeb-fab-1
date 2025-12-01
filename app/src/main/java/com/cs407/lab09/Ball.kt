package com.cs407.lab09

/**
 * Represents a ball that can move. (No Android UI imports!)
 *
 * Constructor parameters:
 * - backgroundWidth: the width of the background, of type Float
 * - backgroundHeight: the height of the background, of type Float
 * - ballSize: the width/height of the ball, of type Float
 */
class Ball(
    private val backgroundWidth: Float,
    private val backgroundHeight: Float,
    private val ballSize: Float
) {
    var posX = 0f
    var posY = 0f
    var velocityX = 0f
    var velocityY = 0f
    private var accX = 0f
    private var accY = 0f

    private var isFirstUpdate = true

    init {
        reset()
        // TODO: Call reset()
    }

    /**
     * Updates the ball's position and velocity based on the given acceleration and time step.
     * (See lab handout for physics equations)
     */
    fun updatePositionAndVelocity(xAcc: Float, yAcc: Float, dT: Float) {
        android.util.Log.d("Ball", "updatePositionAndVelocity called: xAcc=$xAcc, yAcc=$yAcc, dt=$dT")
        if(isFirstUpdate) {
            isFirstUpdate = false
            accX = xAcc
            accY = yAcc
            return
        }

        val oldAccX = accX
        val oldAccY = accY

        accX = xAcc
        accY = yAcc

        val dt = dT

        val newVelX = velocityX + 0.5f * (oldAccX + accX) * dt
        val dx = velocityX * dt + (dt * dt / 6f) * (3f * oldAccX + accX)

        val newVelY = velocityY + 0.5f * (oldAccY + accY) * dt
        val dy = velocityY * dt + (dt * dt / 6f) * (3f * oldAccY + accY)

        velocityY = newVelY
        velocityX = newVelX
        posX += dx
        posY += dy

    }

    /**
     * Ensures the ball does not move outside the boundaries.
     * When it collides, velocity and acceleration perpendicular to the
     * boundary should be set to 0.
     */
    fun checkBoundaries() {
        val maxX = backgroundWidth - ballSize
        if (posX > maxX) {
            posX = maxX
            velocityX = 0f
            accX = 0f
        }

        if (posX < 0f) {
            posX = 0f
            velocityX = 0f
            accX = 0f
        }

        val maxY = backgroundHeight - ballSize
        if (posY > maxY) {
            posY = maxY
            velocityY = 0f
            accY = 0f
        }

        if (posY < 0f) {
            posY = 0f
            velocityY = 0f
            accY = 0f
        }
        // TODO: implement the checkBoundaries function
        // (Check all 4 walls: left, right, top, bottom)
    }

    /**
     * Resets the ball to the center of the screen with zero
     * velocity and acceleration.
     */
    fun reset() {
        android.util.Log.d("Ball", "Ball.reset called")
        posX = (backgroundWidth - ballSize) / 2f
        posY = (backgroundHeight - ballSize) / 2f

        velocityX = 0f
        velocityY = 0f
        accX = 0f
        accY = 0f

        isFirstUpdate = true
        // TODO: implement the reset function
        // (Reset posX, posY, velocityX, velocityY, accX, accY, isFirstUpdate)
    }
}