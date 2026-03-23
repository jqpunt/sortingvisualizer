package nl.justin.sortingvisualizer.view

import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class BarChartView(val canvas: Canvas) {

    private val gc: GraphicsContext = canvas.graphicsContext2D
    private var highlightedIndices: List<Int> = emptyList()

    fun draw(list: List<Int>, highlightedIndices: List<Int> = emptyList()) {
        this.highlightedIndices = highlightedIndices
        gc.clearRect(0.0, 0.0, canvas.width, canvas.height)

        val barWidth = canvas.width / list.size
        val maxVal = list.maxOrNull() ?: 1

        list.forEachIndexed { index, value ->
            val heightRatio = value.toDouble() / maxVal
            val barHeight = canvas.height * heightRatio

            // Highlight bars
            gc.fill = if (index in highlightedIndices) Color.RED else Color.BLUE
            
            gc.fillRect(
                index * barWidth,
                canvas.height - barHeight,
                barWidth - 5,
                barHeight
            )
            
            // Add number to specific bar
            gc.fill = Color.BLACK
            gc.fillText(
                value.toString(),
                index * barWidth + (barWidth - 5) / 2 - 5,
                canvas.height - barHeight - 5
            )
        }
    }
}