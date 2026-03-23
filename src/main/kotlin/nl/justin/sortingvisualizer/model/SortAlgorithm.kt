package nl.justin.sortingvisualizer.model

interface SortAlgorithm {
    fun sort(list: MutableList<Int>): List<List<Int>>
}
