package nl.justin.sortingvisualizer.model

class InsertionSort : nl.justin.sortingvisualizer.model.AbstractSort() {

    override fun performSort(list: MutableList<Int>) {
        for (i in 1 until list.size) {
            val key = list[i]
            var j = i - 1

            while (j >= 0 && list[j] > key) {
                list[j + 1] = list[j]
                j--
                saveStep(list, "Move ${list[j + 1]} to right", listOf(j + 1, j + 2))
            }
            list[j + 1] = key
            saveStep(list, "Add $key to index ${j + 1}", listOf(j + 1))
        }
    }
}
