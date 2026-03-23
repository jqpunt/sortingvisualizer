package nl.justin.sortingvisualizer.view

import javafx.scene.control.TextArea
import nl.justin.sortingvisualizer.model.SortStep

class TextView(val textArea: TextArea) {

    fun displaySteps(steps: List<List<Int>>) {
        textArea.clear()

        steps.forEachIndexed { index, step ->
            textArea.appendText("Step $index: ")
            textArea.appendText(step.toString())
            textArea.appendText("\n")
        }
    }

    fun displayDetailedSteps(steps: List<nl.justin.sortingvisualizer.model.SortStep>) {
        textArea.clear()

        steps.forEachIndexed { index, step ->
            textArea.appendText("Step ${index + 1}:\n")
            textArea.appendText("  Status: ${step.list}\n")
            if (step.description.isNotEmpty()) {
                textArea.appendText("  Action: ${step.description}\n")
            }
            textArea.appendText("\n")
        }
    }

    fun displayMessage(message: String) {
        textArea.clear()
        textArea.appendText(message)
    }

    fun updateCurrentStep(step: Int, total: Int, description: String = "") {
        textArea.clear()
        textArea.appendText("Step: $step / $total\n\n")
        if (description.isNotEmpty()) {
            textArea.appendText("Action:\n$description")
        }
    }
}