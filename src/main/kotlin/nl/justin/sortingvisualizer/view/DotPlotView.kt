package nl.justin.sortingvisualizer.view

import SortView
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import kotlin.math.max
import kotlin.math.min

class DotPlotView(val canvas: Canvas) : SortView {

    private val gc: GraphicsContext = canvas.graphicsContext2D

    override fun draw(list: List<Int>, highlightedIndices: List<Int>) {
        gc.clearRect(0.0, 0.0, canvas.width, canvas.height)
        if (list.isEmpty()) return

        val maxVal = list.maxOrNull() ?: 1
        val minVal = list.minOrNull() ?: 0
        val range = max(1, maxVal - minVal)

        val paddingX = 20.0
        val paddingY = 20.0

        val usableW = max(1.0, canvas.width - 2 * paddingX)
        val usableH = max(1.0, canvas.height - 2 * paddingY)

        val stepX = usableW / max(1, list.size - 1)

        val radius = min(10.0, max(3.0, usableW / (list.size * 3.0)))

        gc.stroke = Color.LIGHTGRAY
        gc.strokeLine(paddingX, canvas.height - paddingY, canvas.width - paddingX, canvas.height - paddingY)

        list.forEachIndexed { index, value ->
            val norm = (value - minVal).toDouble() / range.toDouble()
            val x = paddingX + index * stepX
            val y = (canvas.height - paddingY) - (norm * usableH)

            gc.fill = if (index in highlightedIndices) Color.RED else Color.DODGERBLUE
            gc.fillOval(x - radius, y - radius, radius * 2, radius * 2)

            gc.fill = Color.BLACK
            gc.fillText(value.toString(), x - radius, y - radius - 2)
        }
    }
}