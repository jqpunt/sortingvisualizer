package nl.justin.sortingvisualizer.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class SortingAlgorithmsParameterizedTest {

    companion object {
        @JvmStatic
        fun algorithms(): Stream<SortAlgorithm> = Stream.of(
            BubbleSort(),
            InsertionSort()
        )
    }

    @ParameterizedTest(name = "{index} => algorithm={0}")
    @MethodSource("algorithms")
    fun `should sort unsorted list correctly`(algorithm: SortAlgorithm) {
        val input = mutableListOf(5, 3, 8, 1)
        val result = algorithm.sort(input)

        // bij lege result zouden we hier falen, dus check:
        assertTrue(result.isNotEmpty(), "Expected at least one state/step")
        assertEquals(listOf(1, 3, 5, 8), result.last())
    }

    @ParameterizedTest(name = "{index} => algorithm={0}")
    @MethodSource("algorithms")
    fun `should handle empty list`(algorithm: SortAlgorithm) {
        val input = mutableListOf<Int>()
        val result = algorithm.sort(input)

        // jullie huidige implementatie kan empty teruggeven; beide is oké:
        assertTrue(result.isEmpty() || result.last().isEmpty())
    }

    @ParameterizedTest(name = "{index} => algorithm={0}")
    @MethodSource("algorithms")
    fun `should handle single element`(algorithm: SortAlgorithm) {
        val input = mutableListOf(42)
        val result = algorithm.sort(input)

        val finalState = result.lastOrNull() ?: listOf(42)
        assertEquals(listOf(42), finalState)
    }

    @ParameterizedTest(name = "{index} => algorithm={0}")
    @MethodSource("algorithms")
    fun `should handle duplicates`(algorithm: SortAlgorithm) {
        val input = mutableListOf(4, 2, 2, 1)
        val result = algorithm.sort(input)

        assertTrue(result.isNotEmpty())
        assertEquals(listOf(1, 2, 2, 4), result.last())
    }

    @ParameterizedTest(name = "{index} => algorithm={0}")
    @MethodSource("algorithms")
    fun `should keep already sorted list unchanged`(algorithm: SortAlgorithm) {
        val input = mutableListOf(1, 2, 3, 4)
        val result = algorithm.sort(input)

        assertTrue(result.isNotEmpty())
        assertEquals(listOf(1, 2, 3, 4), result.last())
    }

    @ParameterizedTest(name = "{index} => algorithm={0}")
    @MethodSource("algorithms")
    fun `should handle negative numbers`(algorithm: SortAlgorithm) {
        val input = mutableListOf(-5, 3, -1, 0)
        val result = algorithm.sort(input)

        assertTrue(result.isNotEmpty())
        assertEquals(listOf(-5, -1, 0, 3), result.last())
    }

    @ParameterizedTest(name = "{index} => algorithm={0}")
    @MethodSource("algorithms")
    fun `final state should be sorted`(algorithm: SortAlgorithm) {
        val input = mutableListOf(3, 1, 2, 2, -1)
        val result = algorithm.sort(input)

        assertTrue(result.isNotEmpty())
        val last = result.last()
        assertEquals(last.sorted(), last)
    }
}