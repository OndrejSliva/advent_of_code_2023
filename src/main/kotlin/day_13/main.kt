package day_13

fun main() {
    val lines =
        object {}.javaClass.classLoader.getResourceAsStream("advent_of_code_2023_13.txt")?.bufferedReader()?.readLines()
            ?.stream()?.toList()!!

    val patterns = parsePatterns(lines)
    val sumOfReflectionNumbers = patterns.map { it.findReflectionNumber() }.sum()
    println(sumOfReflectionNumbers)
    val sumOfSmuggedReflectionNumbers = patterns.map { it.findSmudgeReflectionNumber() }.sum()
    println(sumOfSmuggedReflectionNumbers)
}

fun parsePatterns(inputString: List<String>): List<Pattern> {
    val parsedPatterns = arrayListOf<Pattern>()
    var currentPattern = arrayListOf<String>()
    inputString.forEach {
        if (it.isEmpty()) {
            if (currentPattern.isNotEmpty()) {
                parsedPatterns.add(Pattern(currentPattern))
                currentPattern = arrayListOf()
            }
        } else {
            currentPattern.add(it)
        }
    }
    if (currentPattern.isNotEmpty()) {
        parsedPatterns.add(Pattern(currentPattern))
    }

    return parsedPatterns
}

fun isRowSymmetricalByIdx(row: String, idx: Int): Boolean {
    var currentLeftIdx = idx - 1
    var currentRightIdx = idx
    while (currentLeftIdx >= 0 && currentRightIdx <= row.lastIndex) {
        if (row[currentLeftIdx] != row[currentRightIdx]) {
            return false
        }
        currentLeftIdx--
        currentRightIdx++
    }
    return true
}