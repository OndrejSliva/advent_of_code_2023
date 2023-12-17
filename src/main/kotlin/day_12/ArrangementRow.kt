package day_12

import java.util.LinkedList
import java.util.Queue

data class ArrangementRow(
    val row: String,
    val broken: List<Int>
) {
    fun countPossibleArrangements(): Int {
        val possibilityQueue: Queue<String> = LinkedList()
        possibilityQueue.add(row)
        var possibilities = 0
        while (possibilityQueue.isNotEmpty()) {
            val currentRow = possibilityQueue.poll()
            if (isFinal(currentRow)) {
                if (isRowPossible(currentRow, broken)) {
                    possibilities++
                }
            } else {
                val dotRow = currentRow.replaceFirst('?', '.')
                val hashRow = currentRow.replaceFirst('?', '#')
                possibilityQueue.add(dotRow)
                possibilityQueue.add(hashRow)
            }
        }
        return possibilities
    }

    private fun isFinal(row: String) = !row.contains("?")

    private fun isRowPossible(finalRow: String, brokenSequence: List<Int>): Boolean {
        val brokenSizes = arrayListOf<Int>()
        var index = 0
        var brokenSize = 0
        while (index < finalRow.length) {
            if (finalRow[index] == '#') {
                brokenSize++
            } else if (brokenSize > 0) {
                brokenSizes.add(brokenSize)
                brokenSize = 0
            }
            index++
        }
        if (brokenSize > 0) {
            brokenSizes.add(brokenSize)
        }
        return brokenSizes == brokenSequence
    }
}