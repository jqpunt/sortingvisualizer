package nl.justin.sortingvisualizer.model

data class SortStep(
    val list: List<Int>,
    val description: String = "",
    val highlightedIndices: List<Int> = emptyList()
)

abstract class AbstractSort : nl.justin.sortingvisualizer.model.SortAlgorithm {

    protected val steps = mutableListOf<nl.justin.sortingvisualizer.model.SortStep>()

    protected fun saveStep(list: List<Int>, description: String = "", highlightedIndices: List<Int> = emptyList()) {
        steps.add(
            nl.justin.sortingvisualizer.model.SortStep(
                list.toList(),
                description,
                highlightedIndices
            )
        )
    }

    override fun sort(list: MutableList<Int>): List<List<Int>> {
        steps.clear()
        performSort(list)
        return steps.map { it.list }
    }

    fun getSortSteps(list: MutableList<Int>): List<nl.justin.sortingvisualizer.model.SortStep> {
        steps.clear()
        performSort(list)
        return steps
    }

    protected abstract fun performSort(list: MutableList<Int>)
}
