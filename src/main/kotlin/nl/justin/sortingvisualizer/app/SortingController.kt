package nl.justin.sortingvisualizer.app

import nl.justin.sortingvisualizer.model.SortAlgorithm
import nl.justin.sortingvisualizer.model.SortStep
import nl.justin.sortingvisualizer.model.AbstractSort

class SortingController(private val algorithm: nl.justin.sortingvisualizer.model.SortAlgorithm) {

    fun execute(list: MutableList<Int>): List<List<Int>> {
        return algorithm.sort(list)
    }

    fun executeWithDetails(list: MutableList<Int>): List<nl.justin.sortingvisualizer.model.SortStep> {
        return when (algorithm) {
            is nl.justin.sortingvisualizer.model.AbstractSort -> algorithm.getSortSteps(list)
            else -> emptyList()
        }
    }
}