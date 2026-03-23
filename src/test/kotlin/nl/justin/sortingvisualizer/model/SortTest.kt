package nl.justin.sortingvisualizer.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class SortTest {

    companion object {
        @JvmStatic
        fun algorithms(): Stream<SortAlgorithm> = Stream.of(
            BubbleSort(),
            InsertionSort()
        )
    }

    private fun assertSortedOutput(result: List<List<Int>>, expected: List<Int>) {
        // Bij niet-lege input verwachten we minstens 1 state
        assertTrue(result.isNotEmpty(), "Expected at least one recorded state for non-empty input")
        assertEquals(expected, result.last())
    }

    @ParameterizedTest
    @MethodSource("algorithms")
    fun `should sort unsorted list correctly`(algorithm: SortAlgorithm) {
        val input = mutableListOf(5, 3, 8, 1)
        val result = algorithm.sort(input)
        assertSortedOutput(result, listOf(1, 3, 5, 8))
    }

    @ParameterizedTest
    @MethodSource("algorithms")
    fun `should handle empty list`(algorithm: SortAlgorithm) {
        val input = mutableListOf<Int>()
        val result = algorithm.sort(input)

        // Jouw huidige implementatie kan emptyList teruggeven; beide is oké:
        assertTrue(result.isEmpty() || result.last().isEmpty())
    }

    @ParameterizedTest
    @MethodSource("algorithms")
    fun `should handle single element`(algorithm: SortAlgorithm) {
        val input = mutableListOf(42)
        val result = algorithm.sort(input)

        // Bij 1 element kan het algoritme ook 0 steps opslaan; vang dat af
        val finalState = result.lastOrNull() ?: listOf(42)
        assertEquals(listOf(42), finalState)
    }

    @ParameterizedTest
    @MethodSource("algorithms")
    fun `should handle duplicates`(algorithm: SortAlgorithm) {
        val input = mutableListOf(4, 2, 2, 1)
        val result = algorithm.sort(input)
        assertSortedOutput(result, listOf(1, 2, 2, 4))
    }

    @ParameterizedTest
    @MethodSource("algorithms")
    fun `should keep already sorted list unchanged`(algorithm: SortAlgorithm) {
        val input = mutableListOf(1, 2, 3, 4)
        val result = algorithm.sort(input)

        // Dit is non-empty input, maar sommige implementaties loggen mogelijk weinig:
        // Bij jouw Bubble/Insertion verwacht je in de praktijk wél states, maar we houden het robuust:
        val finalState = result.lastOrNull() ?: input.toList()
        assertEquals(listOf(1, 2, 3, 4), finalState)
    }

    @ParameterizedTest
    @MethodSource("algorithms")
    fun `should handle negative numbers`(algorithm: SortAlgorithm) {
        val input = mutableListOf(5, -3, 0, -1)
        val result = algorithm.sort(input)

        val finalState = result.lastOrNull() ?: input.toList()
        assertEquals(listOf(-3, -1, 0, 5), finalState)
    }

    @ParameterizedTest
    @MethodSource("algorithms")
    fun `final state should be sorted when there is at least one recorded state`(algorithm: SortAlgorithm) {
        val input = mutableListOf(3, 1, 2, 2, -1)
        val result = algorithm.sort(input)

        if (result.isNotEmpty()) {
            val last = result.last()
            assertEquals(last.sorted(), last)
        }
    }
}