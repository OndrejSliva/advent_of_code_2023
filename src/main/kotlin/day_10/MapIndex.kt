package day_10

data class MapIndex(
    val rowIdx: Int,
    val colIdx: Int,
) {
    fun left(): MapIndex {
        return MapIndex(rowIdx, colIdx - 1)
    }

    fun bottom(): MapIndex {
        return MapIndex(rowIdx + 1, colIdx)
    }

    fun right(): MapIndex {
        return MapIndex(rowIdx, colIdx + 1)
    }

    fun top(): MapIndex {
        return MapIndex(rowIdx - 1, colIdx)
    }

    fun leftTop(): MapIndex {
        return MapIndex(rowIdx - 1, colIdx - 1)
    }

    fun leftBottom(): MapIndex {
        return MapIndex(rowIdx + 1, colIdx - 1)
    }

    fun rightTop(): MapIndex {
        return MapIndex(rowIdx - 1, colIdx + 1)
    }

    fun rightBottom(): MapIndex {
        return MapIndex(rowIdx + 1, colIdx + 1)
    }

    fun isOut(width: Int, height: Int): Boolean {
        return rowIdx < 0 || colIdx < 0 || rowIdx >= height || colIdx >= width
    }
}
