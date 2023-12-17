package day_11

import kotlin.math.abs

data class GalaxyIndex(
    val rowIdx: Int,
    val colIdx: Int,
) {
    fun distance(otherGalaxyIndex: GalaxyIndex): Int {
        return abs(rowIdx - otherGalaxyIndex.rowIdx) + abs(colIdx - otherGalaxyIndex.colIdx)
    }
    fun distance(otherGalaxyIndex: GalaxyIndex, emptyLineIndexes: List<Int>, emptyColumnIndexes: List<Int>, emptyMultiplier: Int): Int {
        val emptyBetween =
            emptyLineIndexes.filter { rowIdx < it && it < otherGalaxyIndex.rowIdx || otherGalaxyIndex.rowIdx < it && it < rowIdx }.size +
            emptyColumnIndexes.filter { colIdx < it && it < otherGalaxyIndex.colIdx || otherGalaxyIndex.colIdx < it && it < colIdx }.size
        return abs(rowIdx - otherGalaxyIndex.rowIdx) + abs(colIdx - otherGalaxyIndex.colIdx) + emptyBetween * emptyMultiplier - emptyBetween
    }
}