package day_03

data class NumberPosition(
    val line: Int,
    val column: Int,
    val size: Int,
    val number: Int
) {
    fun columnLastIdx() = column + size - 1
}