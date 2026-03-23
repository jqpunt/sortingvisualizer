package nl.justin.sortingvisualizer.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

abstract class AbstractAlgorithmTest {

    protected abstract fun createAlgorithm(): SortAlgorithm

    private fun algorithm(): SortAlgorithm = createAlgorithm()


    @Test
    fun `should sort unsorted list correctly`() {
        val result = algorithm().sort(mutableListOf(5, 3, 8, 1))
        assertTrue(result.isNotEmpty(), "Expected at least one recorded state for non-empty input")
        assertEquals(listOf(1, 3, 5, 8), result.last())
    }

    @Test
    fun `should handle empty list`() {
        val result = algorithm().sort(mutableListOf())
        assertTrue(result.isEmpty() || result.last().isEmpty())
    }

    @Test
    fun `should handle single element`() {
        val result = algorithm().sort(mutableListOf(42))
        val finalState = result.lastOrNull() ?: listOf(42)
        assertEquals(listOf(42), finalState)
    }

    @Test
    fun `should handle duplicates`() {
        val result = algorithm().sort(mutableListOf(4, 2, 2, 1))
        assertTrue(result.isNotEmpty())
        assertEquals(listOf(1, 2, 2, 4), result.last())
    }

    @Test
    fun `should keep already sorted list unchanged`() {
        val input = mutableListOf(1, 2, 3, 4)
        val result = algorithm().sort(input)
        val finalState = result.lastOrNull() ?: input.toList()
        assertEquals(listOf(1, 2, 3, 4), finalState)
    }

    @Test
    fun `should handle negative numbers`() {
        val input = mutableListOf(5, -3, 0, -1)
        val result = algorithm().sort(input)
        val finalState = result.lastOrNull() ?: input.toList()
        assertEquals(listOf(-3, -1, 0, 5), finalState)
    }

    @Test
    fun `final state should be sorted when there is at least one recorded state`() {
        val result = algorithm().sort(mutableListOf(3, 1, 2, 2, -1))
        if (result.isNotEmpty()) {
            val last = result.last()
            assertEquals(last.sorted(), last)
        }
    }


    @Test
    fun `if algorithm supports steps then steps should contain descriptions and highlights`() {
        val alg = algorithm()

        // Alleen testen als dit algoritme daadwerkelijk step-recording ondersteunt
        if (alg is AbstractSort) {
            val steps = alg.getSortSteps(mutableListOf(3, 1, 2))
            assertTrue(steps.isNotEmpty(), "Expected steps for non-empty input")

            assertTrue(steps.any { it.description.isNotBlank() }, "Expected at least one non-blank description")
            assertTrue(steps.any { it.highlightedIndices.isNotEmpty() }, "Expected at least one highlighted step")

            val last = steps.last().list
            assertEquals(last.sorted(), last)
        }
    }
}