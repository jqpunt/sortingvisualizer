package nl.justin.sortingvisualizer.view

import SortView
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class BarChartView(val canvas: Canvas) : SortView {

    private val gc: GraphicsContext = canvas.graphicsContext2D
    private var highlightedIndices: List<Int> = emptyList()

    override fun draw(list: List<Int>, highlightedIndices: List<Int>) {
        this.highlightedIndices = highlightedIndices
        gc.clearRect(0.0, 0.0, canvas.width, canvas.height)

        if (list.isEmpty()) return

        val barWidth = canvas.width / list.size
        val maxVal = list.maxOrNull() ?: 1

        list.forEachIndexed { index, value ->
            val heightRatio = value.toDouble() / maxVal
            val barHeight = canvas.height * heightRatio

            gc.fill = if (index in highlightedIndices) Color.RED else Color.BLUE

            gc.fillRect(
                index * barWidth,
                canvas.height - barHeight,
                barWidth - 5,
                barHeight
            )

            gc.fill = Color.BLACK
            gc.fillText(
                value.toString(),
                index * barWidth + (barWidth - 5) / 2 - 5,
                canvas.height - barHeight - 5
            )
        }
    }
}