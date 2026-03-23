package nl.justin.sortingvisualizer.model

import nl.justin.sortingvisualizer.model.BubbleSort
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class BubbleSortTest {

    private val bubbleSort = nl.justin.sortingvisualizer.model.BubbleSort()

    @Test
    fun `should sort unsorted list correctly`() {
        val input = mutableListOf(5, 3, 8, 1)
        val result = bubbleSort.sort(input)
        val finalState = result.last()
        assertEquals(listOf(1, 3, 5, 8), finalState)
    }

    @Test
    fun `should handle empty list`() {
        val input = mutableListOf<Int>()
        val result = bubbleSort.sort(input)
        assertTrue(result.isEmpty() || result.last().isEmpty())
    }

    @Test
    fun `should handle single element`() {
        val input = mutableListOf(42)
        val result = bubbleSort.sort(input)
        val finalState = result.lastOrNull() ?: listOf(42)
        assertEquals(listOf(42), finalState)
    }

    @Test
    fun `should handle duplicates`() {
        val input = mutableListOf(4, 2, 2, 1)
        val result = bubbleSort.sort(input)
        val finalState = result.last()
        assertEquals(listOf(1, 2, 2, 4), finalState)
    }

    @Test
    fun `should keep already sorted list unchanged`() {
        val input = mutableListOf(1, 2, 3, 4)
        val result = bubbleSort.sort(input)
        val finalState = result.last()
        assertEquals(listOf(1, 2, 3, 4), finalState)
    }

    @Test
    fun `should record sort steps with details`() {
        val input = mutableListOf(3, 1, 2)
        val steps = bubbleSort.getSortSteps(input)
        
        assertTrue(steps.isNotEmpty())
        assertTrue(steps.first().description.isNotEmpty())
        steps.forEach { step ->
            assertNotNull(step.list)
            assertNotNull(step.description)
            assertNotNull(step.highlightedIndices)
        }
    }

    @Test
    fun `sort step should contain proper data`() {
        val input = mutableListOf(2, 1)
        val steps = bubbleSort.getSortSteps(input)
        
        assertTrue(steps.isNotEmpty())
        assertTrue(steps.last().list.sorted() == steps.last().list)
    }

    @Test
    fun `should handle negative numbers`() {
        val input = mutableListOf(-5, 3, -1, 0)
        val result = bubbleSort.sort(input)
        val finalState = result.last()
        assertEquals(listOf(-5, -1, 0, 3), finalState)
    }

    @Test
    fun `should record highlighted indices during swaps`() {
        val input = mutableListOf(3, 1, 2)
        val steps = bubbleSort.getSortSteps(input)
        
        assertTrue(steps.isNotEmpty())
        val hasHighlights = steps.any { it.highlightedIndices.isNotEmpty() }
        assertTrue(hasHighlights)
    }
}
