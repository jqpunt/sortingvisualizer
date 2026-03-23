package nl.justin.sortingvisualizer.app

import SortView
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.stage.Stage
import nl.justin.sortingvisualizer.model.BubbleSort
import nl.justin.sortingvisualizer.model.InsertionSort
import nl.justin.sortingvisualizer.model.SortAlgorithm
import nl.justin.sortingvisualizer.view.BarChartView
import nl.justin.sortingvisualizer.view.DotPlotView
import nl.justin.sortingvisualizer.view.TextView

class MainApp : Application() {

    private lateinit var list: MutableList<Int>

    // One canvas, multiple visualizations rendering to it
    private lateinit var canvas: Canvas
    private lateinit var barView: BarChartView
    private lateinit var dotView: DotPlotView
    private lateinit var activeView: SortView

    private lateinit var textView: TextView
    private lateinit var animationController: AnimationController

    // UI Components
    private lateinit var bubbleBtn: Button
    private lateinit var insertionBtn: Button
    private lateinit var resetBtn: Button
    private lateinit var playBtn: Button
    private lateinit var pauseBtn: Button
    private lateinit var stepSlider: Slider
    private lateinit var speedSlider: Slider
    private lateinit var visualizationCombo: ComboBox<String>

    override fun start(stage: Stage) {
        list = mutableListOf(5, 3, 8, 1, 2, 7, 4, 6)

        buildUIComponents()
        setupEventHandlers()
        displayUI(stage)
    }

    private fun buildUIComponents() {
        canvas = Canvas(400.0, 250.0)
        barView = BarChartView(canvas)
        dotView = DotPlotView(canvas)
        activeView = barView

        val textArea = TextArea().apply {
            prefHeight = 150.0
            isWrapText = true
        }
        textView = TextView(textArea)

        speedSlider = Slider(10.0, 100.0, 50.0).apply {
            prefWidth = 200.0
            isShowTickLabels = true
            isShowTickMarks = true
        }

        visualizationCombo = ComboBox<String>().apply {
            items.addAll("Bar chart", "Dot plot")
            value = "Bar chart"
        }

        bubbleBtn = Button("Bubble Sort")
        insertionBtn = Button("Insertion Sort")
        resetBtn = Button("Reset")
        playBtn = Button("Play")
        pauseBtn = Button("Pause")

        stepSlider = Slider(0.0, 100.0, 0.0).apply {
            prefWidth = 300.0
            blockIncrement = 1.0
        }

        animationController = AnimationController(
            activeView,
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
        bubbleBtn.setOnAction { executeSortAlgorithm(BubbleSort()) }
        insertionBtn.setOnAction { executeSortAlgorithm(InsertionSort()) }

        playBtn.setOnAction { animationController.play() }
        pauseBtn.setOnAction { animationController.pause() }
        resetBtn.setOnAction { animationController.reset() }

        stepSlider.setOnMouseReleased { animationController.goToStep(stepSlider.value.toInt()) }

        visualizationCombo.setOnAction {
            activeView = if (visualizationCombo.value == "Dot plot") dotView else barView
            animationController.setView(activeView)
        }
    }

    private fun executeSortAlgorithm(algorithm: SortAlgorithm) {
        enableControls()
        val steps = SortingController(algorithm).executeWithDetails(list.toMutableList())
        if (steps.isEmpty()) return

        animationController.setSteps(steps)
        activeView.draw(steps.first().list, steps.first().highlightedIndices)
        textView.updateCurrentStep(0, steps.size - 1, steps.first().description)
    }

    private fun enableControls() {
        playBtn.isDisable = false
        pauseBtn.isDisable = false
        resetBtn.isDisable = false
    }

    private fun displayUI(stage: Stage) {
        val speedLabel = Label("Speed:")
        val speedBox = HBox(5.0, speedLabel, speedSlider)

        val vizLabel = Label("Visualization:")
        val vizBox = HBox(5.0, vizLabel, visualizationCombo)

        val stepLabel = Label("Step:")
        val stepBox = HBox(5.0, stepLabel, stepSlider)

        val controlBox = HBox(10.0, bubbleBtn, insertionBtn, playBtn, pauseBtn, resetBtn)

        val mainBox = VBox(
            10.0,
            controlBox,
            speedBox,
            vizBox,
            canvas,
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
    Application.launch(MainApp::class.java)
}