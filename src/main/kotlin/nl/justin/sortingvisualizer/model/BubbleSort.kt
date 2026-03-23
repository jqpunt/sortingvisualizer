package nl.justin.sortingvisualizer.model

class BubbleSort : nl.justin.sortingvisualizer.model.AbstractSort() {

    override fun performSort(list: MutableList<Int>) {
        for (i in list.indices) {
            for (j in 0 until list.size - i - 1) {
                if (list[j] > list[j + 1]) {
                    val temp = list[j]
                    list[j] = list[j + 1]
                    list[j + 1] = temp
                    saveStep(list, "Swap ${temp} and ${list[j]} at index $j and ${j + 1}", listOf(j, j + 1))
                } else {
                    saveStep(list, "Compare ${list[j]} and ${list[j + 1]} - No swap!", listOf(j, j + 1))
                }
            }
        }
    }
}
