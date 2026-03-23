package nl.justin.sortingvisualizer.app

import nl.justin.sortingvisualizer.model.BubbleSort
import nl.justin.sortingvisualizer.model.InsertionSort
import nl.justin.sortingvisualizer.model.SortAlgorithm
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.stage.Stage
import nl.justin.sortingvisualizer.view.BarChartView
import nl.justin.sortingvisualizer.view.TextView


class MainApp : Application() {

    private lateinit var list: MutableList<Int>
    private lateinit var barView: nl.justin.sortingvisualizer.view.BarChartView
    private lateinit var textView: nl.justin.sortingvisualizer.view.TextView
    private lateinit var animationController: nl.justin.sortingvisualizer.app.AnimationController
    
    // UI Components
    private lateinit var bubbleBtn: Button
    private lateinit var insertionBtn: Button
    private lateinit var resetBtn: Button
    private lateinit var playBtn: Button
    private lateinit var pauseBtn: Button
    private lateinit var stepSlider: Slider

    override fun start(stage: Stage) {
        list = mutableListOf(5, 3, 8, 1, 2, 7, 4, 6)
        
        buildUIComponents()
        setupEventHandlers()
        displayUI(stage)
    }

    private fun buildUIComponents() {
        val canvas = Canvas(400.0, 250.0)
        barView = nl.justin.sortingvisualizer.view.BarChartView(canvas)

        val textArea = TextArea().apply {
            prefHeight = 150.0
            isWrapText = true
        }
        textView = nl.justin.sortingvisualizer.view.TextView(textArea)

        // Speed slider
        val speedSlider = Slider(10.0, 100.0, 50.0).apply {
            prefWidth = 200.0
            isShowTickLabels = true
            isShowTickMarks = true
        }

        // Buttons
        bubbleBtn = Button("Bubble Sort")
        insertionBtn = Button("Insertion Sort")
        resetBtn = Button("Reset")
        playBtn = Button("Play")
        pauseBtn = Button("Pause")

        // Step slider
        stepSlider = Slider(0.0, 100.0, 0.0).apply {
            prefWidth = 300.0
            blockIncrement = 1.0
        }

        animationController = nl.justin.sortingvisualizer.app.AnimationController(
            barView,
            speedSlider
        ) { currentStep, totalSteps, description, _ ->
            stepSlider.max = totalSteps.toDouble()
            stepSlider.value = currentStep.toDouble()
            textView.updateCurrentStep(currentStep, totalSteps, description)
        }

        playBtn.isDisable = true
        pauseBtn.isDisable = true
        resetBtn.isDisable = true
    }

    private fun setupEventHandlers() {
        bubbleBtn.setOnAction { executeSortAlgorithm(nl.justin.sortingvisualizer.model.BubbleSort()) }
        insertionBtn.setOnAction { executeSortAlgorithm(nl.justin.sortingvisualizer.model.InsertionSort()) }
        playBtn.setOnAction { animationController.play() }
        pauseBtn.setOnAction { animationController.pause() }
        resetBtn.setOnAction { animationController.reset() }
        stepSlider.setOnMouseReleased { animationController.goToStep(stepSlider.value.toInt()) }
    }

    private fun executeSortAlgorithm(algorithm: nl.justin.sortingvisualizer.model.SortAlgorithm) {
        enableControls()
        val steps = nl.justin.sortingvisualizer.app.SortingController(algorithm).executeWithDetails(list.toMutableList())
        animationController.setSteps(steps)
        barView.draw(steps.first().list)
        textView.updateCurrentStep(0, steps.size - 1, steps.first().description)
    }

    private fun enableControls() {
        playBtn.isDisable = false
        pauseBtn.isDisable = false
        resetBtn.isDisable = false
    }

    private fun displayUI(stage: Stage) {
        val speedLabel = Label("Speed:")
        val speedSlider = Slider(10.0, 100.0, 50.0).apply {
            prefWidth = 200.0
            isShowTickLabels = true
            isShowTickMarks = true
        }
        val speedBox = HBox(5.0, speedLabel, speedSlider)

        val stepLabel = Label("Step:")
        val stepBox = HBox(5.0, stepLabel, stepSlider)

        val controlBox = HBox(10.0, bubbleBtn, insertionBtn, playBtn, pauseBtn, resetBtn)
        
        val mainBox = VBox(
            10.0,
            controlBox,
            speedBox,
            barView.canvas,
            stepBox,
            textView.textArea
        )
        VBox.setVgrow(textView.textArea, Priority.ALWAYS)

        stage.scene = Scene(mainBox, 600.0, 700.0)
        stage.title = "Sorting Visualizer"
        stage.show()
    }
}

fun main() {
    Application.launch(nl.justin.sortingvisualizer.app.MainApp::class.java)
}