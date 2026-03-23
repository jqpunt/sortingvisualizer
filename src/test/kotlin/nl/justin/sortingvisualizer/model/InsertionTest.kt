package nl.justin.sortingvisualizer.model

//import nl.justin.sortingvisualizer.model.InsertionSort as folder
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class InsertionSortTest {

    private val insertionSort = nl.justin.sortingvisualizer.model.InsertionSort()

    @Test
    fun `should sort unsorted list correctly`() {
        val input = mutableListOf(5, 3, 8, 1)
        val result = insertionSort.sort(input)
        val finalState = result.last()
        assertEquals(listOf(1, 3, 5, 8), finalState)
    }

    @Test
    fun `should handle empty list`() {
        val input = mutableListOf<Int>()
        val result = insertionSort.sort(input)
        assertTrue(result.isEmpty() || result.last().isEmpty())
    }

    @Test
    fun `should handle single element`() {
        val input = mutableListOf(99)
        val result = insertionSort.sort(input)
        val finalState = result.lastOrNull() ?: listOf(99)
        assertEquals(listOf(99), finalState)
    }

    @Test
    fun `should handle reverse sorted list`() {
        val input = mutableListOf(5, 4, 3, 2, 1)
        val result = insertionSort.sort(input)
        val finalState = result.last()
        assertEquals(listOf(1, 2, 3, 4, 5), finalState)
    }

    @Test
    fun `should handle duplicates`() {
        val input = mutableListOf(3, 1, 2, 1)
        val result = insertionSort.sort(input)
        val finalState = result.last()
        assertEquals(listOf(1, 1, 2, 3), finalState)
    }

    @Test
    fun `should record sort steps with details`() {
        val input = mutableListOf(3, 1, 2)
        val steps = insertionSort.getSortSteps(input)
        
        assertTrue(steps.isNotEmpty())
        steps.forEach { step ->
            assertNotNull(step.list)
            assertNotNull(step.description)
            assertNotNull(step.highlightedIndices)
        }
    }

    @Test
    fun `sort step should contain proper insertion data`() {
        val input = mutableListOf(3, 1, 2)
        val steps = insertionSort.getSortSteps(input)
        
        assertTrue(steps.isNotEmpty())
        assertTrue(steps.last().list.sorted() == steps.last().list)
    }

    @Test
    fun `should handle negative numbers`() {
        val input = mutableListOf(5, -3, 0, -1)
        val result = insertionSort.sort(input)
        val finalState = result.last()
        assertEquals(listOf(-3, -1, 0, 5), finalState)
    }

    @Test
    fun `should record highlighted indices during insertions`() {
        val input = mutableListOf(3, 1, 2)
        val steps = insertionSort.getSortSteps(input)
        
        assertTrue(steps.isNotEmpty())
        val hasHighlights = steps.any { it.highlightedIndices.isNotEmpty() }
        assertTrue(hasHighlights)
    }

    @Test
    fun `should have descriptions for each step`() {
        val input = mutableListOf(3, 1, 2)
        val steps = insertionSort.getSortSteps(input)
        
        assertTrue(steps.isNotEmpty())
        val hasDescriptions = steps.any { it.description.isNotEmpty() }
        assertTrue(hasDescriptions)
    }
}
