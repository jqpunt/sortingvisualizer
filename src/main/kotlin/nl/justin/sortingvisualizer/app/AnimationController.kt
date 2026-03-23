package nl.justin.sortingvisualizer.app

import javafx.animation.AnimationTimer
import javafx.scene.control.Slider
import nl.justin.sortingvisualizer.model.SortStep
import nl.justin.sortingvisualizer.view.BarChartView

class AnimationController(
    private val barView: nl.justin.sortingvisualizer.view.BarChartView,
    private val speedSlider: Slider,
    private val onStepChanged: (currentStep: Int, totalSteps: Int, description: String, indices: List<Int>) -> Unit
) {

    private val steps = mutableListOf<nl.justin.sortingvisualizer.model.SortStep>()
    private var currentStep = 0
    private var isPlaying = false
    private var animationTimer: AnimationTimer? = null
    private var lastUpdateTime = 0L
    
    fun setSteps(newSteps: List<nl.justin.sortingvisualizer.model.SortStep>) {
        steps.clear()
        steps.addAll(newSteps)
        currentStep = 0
        isPlaying = false
        stopAnimation()
        if (steps.isNotEmpty()) {
            displayStep(0)
        }
    }

    fun play() {
        if (steps.isEmpty()) return
        isPlaying = true

        animationTimer = object : javafx.animation.AnimationTimer() {
            override fun handle(now: Long) {
                if (lastUpdateTime == 0L) {
                    lastUpdateTime = now
                    return
                }

                val speed = speedSlider.value / 100.0
                val interval = (200_000_000 / (speed + 0.5)).toLong()

                if (now - lastUpdateTime > interval) {
                    if (currentStep < steps.size - 1) {
                        currentStep++
                        displayStep(currentStep)
                        lastUpdateTime = now
                    } else {
                        isPlaying = false
                        stop()
                    }
                }
            }
        }.apply { start() }
    }

    fun pause() {
        isPlaying = false
        stopAnimation()
    }

    fun goToStep(step: Int) {
        if (step in 0 until steps.size) {
            currentStep = step
            displayStep(step)
            isPlaying = false
            stopAnimation()
        }
    }

    fun reset() {
        currentStep = 0
        isPlaying = false
        stopAnimation()
        if (steps.isNotEmpty()) {
            displayStep(0)
        }
    }

    fun isPlayingNow() = isPlaying

    private fun stopAnimation() {
        animationTimer?.stop()
        animationTimer = null
        lastUpdateTime = 0L
    }

    private fun displayStep(index: Int) {
        if (index in 0 until steps.size) {
            val step = steps[index]
            barView.draw(step.list, step.highlightedIndices)
            onStepChanged(index, steps.size - 1, step.description, step.highlightedIndices)
        }
    }
}