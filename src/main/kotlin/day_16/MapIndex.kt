package day_16

data class MapIndex(
    val rowIdx: Int,
    val colIdx: Int,
) {
    fun left(): MapIndex {
        return MapIndex(rowIdx, colIdx - 1)
    }

    fun down(): MapIndex {
        return MapIndex(rowIdx + 1, colIdx)
    }

    fun right(): MapIndex {
        return MapIndex(rowIdx, colIdx + 1)
    }

    fun up(): MapIndex {
        return MapIndex(rowIdx - 1, colIdx)
    }

    fun isOut(width: Int, height: Int): Boolean {
        return rowIdx < 0 || colIdx < 0 || rowIdx >= height || colIdx >= width
    }
}
