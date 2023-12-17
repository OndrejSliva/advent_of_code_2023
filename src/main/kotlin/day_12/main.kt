package day_12

import kotlin.collections.HashMap

fun main() {
    val lines =
        object {}.javaClass.classLoader.getResourceAsStream("advent_of_code_2023_12.txt")?.bufferedReader()?.readLines()
            ?.stream()?.toList()!!

    val totalPossibilities = lines.map { parseRow(it) }.sumOf { it.countPossibleArrangements() }

    val cache = hashMapOf<String, Long>()
    val totalPossibilitiesFolded = lines.map { parseRow(it) }.sumOf { countPossibleArrangementsFoldedWithCache(it, 5, cache) }

    println(totalPossibilities)
    println(totalPossibilitiesFolded)
}

fun parseRow(row: String): ArrangementRow {
    val rowAndErrors = row.split(" ")
    return ArrangementRow(
        rowAndErrors[0],
        rowAndErrors[1].split(",").map { it.toInt() }
    )
}

fun countBrokenSizeAtStart(row: String): Int {
    var currentIdx = 0
    var brokenSize = 0
    while (currentIdx < row.length) {
        val currentChar = row[currentIdx]
        if (currentChar == '#' || currentChar == '?') {
            brokenSize++
        } else {
            break
        }
        currentIdx++
    }
    return brokenSize
}

fun connectList(listsToConnect: List<String>, separator: String): String {
    return listsToConnect.joinToString(separator)
}

fun countPossibleArrangementsFoldedWithCache(arrangementRow: ArrangementRow, fold: Int, cache: HashMap<String, Long>): Long {
    val brokenFolded = (1..fold).map { arrangementRow.broken }.flatten()
    val foldedRow = (1..fold).joinToString("?") { arrangementRow.row }

    return countPossibleArrangementsWithCache(ArrangementRow(foldedRow, brokenFolded), cache)
}

fun countPossibleArrangementsWithCache(currentItem: ArrangementRow, cache: HashMap<String, Long>): Long {
    val currentRow = currentItem.row
    val cacheKey = createCacheKey(currentItem)
    if (cache.containsKey(cacheKey)) {
        return cache.getValue(cacheKey)
    }

    if (currentItem.broken.isEmpty()) {
        if (currentRow.contains("#")) {
            return 0
        }
        return 1
    }

    if (currentRow.isEmpty()) {
        return 0
    }

    if (currentRow[0] == '.') {
        val subArrangementRow = ArrangementRow(currentRow.substring(1), currentItem.broken)
        return getResultFromCacheOrEnrichIt(subArrangementRow, cache)
    }
    val brokenSizeAtStart = countBrokenSizeAtStart(currentRow)
    val currentBrokenSizeFromList = currentItem.broken[0]

    if (currentBrokenSizeFromList > currentRow.length) {
        return 0
    }
    val startContainsHash = currentRow.substring(0, brokenSizeAtStart).contains('#')

    if (currentBrokenSizeFromList == brokenSizeAtStart) {
        var withoutHashSum = 0L
        val substringIndex = if(currentRow.length == currentBrokenSizeFromList) currentBrokenSizeFromList else currentBrokenSizeFromList + 1
        if (!startContainsHash) {
            val withoutHashRow = ArrangementRow(currentRow.substring(substringIndex), currentItem.broken)
            withoutHashSum = getResultFromCacheOrEnrichIt(withoutHashRow, cache)
        }
        val skipOneSubRow = ArrangementRow(currentRow.substring(substringIndex), currentItem.broken.stream().skip(1).toList())
        val skiOneSum = getResultFromCacheOrEnrichIt(skipOneSubRow, cache)
        return withoutHashSum + skiOneSum
    } else if (currentBrokenSizeFromList < brokenSizeAtStart) {
        if (currentRow[0] == '#') {
            if (currentRow[currentBrokenSizeFromList] == '?') {     // next empty can be dot
                val subArrangementRow = ArrangementRow(currentRow.substring(currentBrokenSizeFromList + 1),  currentItem.broken.stream().skip(1).toList()) // +1 to be faster
                return getResultFromCacheOrEnrichIt(subArrangementRow, cache)
            }
            return 0
        } else if (currentRow[0] == '?') {
            val skipOneCharSubRow = ArrangementRow(currentRow.substring(1), currentItem.broken)
            val skipOnlyCharSum = getResultFromCacheOrEnrichIt(skipOneCharSubRow, cache)
            var skipCharAndItemSum = 0L
            if (currentRow[currentBrokenSizeFromList] != '#') {     // next is not hash
                val skipMoreSubRow = ArrangementRow(currentRow.substring(currentBrokenSizeFromList + 1), currentItem.broken.stream().skip(1).toList())
                skipCharAndItemSum = getResultFromCacheOrEnrichIt(skipMoreSubRow, cache)
            }
            return skipOnlyCharSum + skipCharAndItemSum
        } else {
            throw Exception("Should not happen")
        }
    } else {
        if (startContainsHash) {
            return 0
        }

        val subArrangementRow = ArrangementRow(currentRow.substring(brokenSizeAtStart + 1), currentItem.broken) // +1 to be faster
        return getResultFromCacheOrEnrichIt(subArrangementRow, cache)
    }
}

private fun getResultFromCacheOrEnrichIt(arrangementRow: ArrangementRow, cache: HashMap<String, Long>): Long {
    val subArrangementRow = ArrangementRow(arrangementRow.row, arrangementRow.broken)
    val result = countPossibleArrangementsWithCache(subArrangementRow, cache)
    val newCacheKey = createCacheKey(subArrangementRow)
    cache[newCacheKey] = result
    return result
}

private fun createCacheKey(arrangementRow: ArrangementRow): String {
    return "${arrangementRow.row}_${arrangementRow.broken.joinToString(",")}"
}