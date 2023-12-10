package day_09

fun main() {
    val lines =
        object {}.javaClass.classLoader.getResourceAsStream("advent_of_code_2023_09.txt")?.bufferedReader()?.readLines()
            ?.stream()?.toList()!!

    val rows = lines.map { parseRow(it) }.toList()
    val nextValuesSum = rows.sumOf { findNext(it) }
    val previousValuesSum = rows.sumOf { findPrevious(it) }
    println(nextValuesSum)
    println(previousValuesSum)
}

fun parseRow(row: String): List<Int> = row.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toList()

fun getDifferences(sequence: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (i in 0 until sequence.size - 1) {
        result.add(sequence[i + 1] - sequence[i])
    }
    return result
}

fun findNext(sequence: List<Int>): Int {
    if (sequence.all { it == 0 }) {
        return 0
    }
    val differences = getDifferences(sequence)
    if (differences.all { it == 0 }) {
        return sequence.last()
    }
    val next = findNext(differences)
    return sequence.last() + next
}

fun findPrevious(sequence: List<Int>): Int {
    if (sequence.all { it == 0 }) {
        return 0
    }
    val differences = getDifferences(sequence)
    if (differences.all { it == 0 }) {
        return sequence[0]
    }
    val previous = findPrevious(differences)
    return sequence[0] - previous
}