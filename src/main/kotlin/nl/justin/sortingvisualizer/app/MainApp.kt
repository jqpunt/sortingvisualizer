import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.ComboBox
import javafx.scene.control.Slider
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.stage.Stage

class MainApp : Application() {
    private lateinit var speedSlider: Slider
    private lateinit var animationController: AnimationController
    private lateinit var canvas: Canvas
    private lateinit var currentView: SortVisualizationView

    override fun start(primaryStage: Stage) {
        val layout = BorderPane()
        speedSlider = Slider(1.0, 10.0, 5.0)
        setupComboBox(layout)
        layout.center = canvas
        layout.bottom = speedSlider
        primaryStage.scene = Scene(layout, 800.0, 600.0)
        primaryStage.show()
    }

    private fun setupComboBox(layout: BorderPane) {
        val comboBox = ComboBox<String>()
        comboBox.items.addAll("Bar Chart", "Dot Plot")
        comboBox.value = "Bar Chart"
        comboBox.setOnAction { updateVisualization(comboBox.value) }
        layout.top = comboBox
    }

    private fun updateVisualization(selected: String) {
        // Update the current view based on the selected item
        currentView = when (selected) {
            "Dot Plot" -> DotPlotView()
            else -> BarChartView()
        }
        canvas = Canvas(currentView.width, currentView.height)
        animationController = AnimationController(currentView)
        // Update your drawing logic accordingly
    }
}

fun main() {
    Application.launch(MainApp::class.java)
}