package nl.justin.sortingvisualizer.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class AbstractSortStepsTest {

    companion object {
        @JvmStatic
        fun stepAlgorithms(): Stream<AbstractSort> = Stream.of(
            BubbleSort(),
            InsertionSort()
        )
    }

    @ParameterizedTest(name = "{index} => algorithm={0}")
    @MethodSource("stepAlgorithms")
    fun `should record sort steps with details`(algorithm: AbstractSort) {
        val input = mutableListOf(3, 1, 2)
        val steps = algorithm.getSortSteps(input)

        assertTrue(steps.isNotEmpty())

        steps.forEach { step ->
            assertNotNull(step.list)
            assertNotNull(step.description)
            assertNotNull(step.highlightedIndices)
        }

        // minstens één stap met description
        assertTrue(steps.any { it.description.isNotBlank() })
        // minstens één stap met highlights (anders heeft visualisatie weinig zin)
        assertTrue(steps.any { it.highlightedIndices.isNotEmpty() })

        // laatste stap moet sorted zijn
        val last = steps.last().list
        assertEquals(last.sorted(), last)
    }
}