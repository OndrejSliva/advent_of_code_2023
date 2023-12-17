package day_13

data class Pattern(
    val pattern: List<String>
) {
    fun findSmudgeReflectionNumber(): Int {
        val ref = findSmudgeReflection()
        if (ref.rows.size == 1) {
            return ref.rows[0]
        }
        return ref.columns[0] * 100
    }

    fun findSmudgeReflection(): ReflectionIndexes {
        val originalReflection = findReflection()
        for (rowIdx in pattern.indices) {
            val row = pattern[rowIdx]
            for (colIdx in row.indices) {
                val mutedList = pattern.toMutableList()
                val newChar = if (mutedList[rowIdx][colIdx] == '.') {
                    "#"
                } else {
                    '.'
                }
                val newRow = "${row.substring(0, colIdx)}$newChar${row.substring(colIdx + 1)}"
                mutedList[rowIdx] = newRow
                val newReflection = Pattern(mutedList).findReflection()
                if (originalReflection != newReflection && (newReflection.rows.isNotEmpty() || newReflection.columns.isNotEmpty())) {
                    val resultRows = newReflection.rows.toMutableList()
                    resultRows.removeAll(originalReflection.rows)
                    val resultCols = newReflection.columns.toMutableList()
                    resultCols.removeAll(originalReflection.columns)
                    return ReflectionIndexes(resultRows, resultCols)
                }
            }
        }

        throw Exception("Reflection not found")
    }

    private fun findReflection(): ReflectionIndexes {
        val symmetricalIndexesInRows = arrayListOf<Set<Int>>()
        for (rowIdx in pattern.indices) {
            val symmetricalRowIndexes = hashSetOf<Int>()
            val row = pattern[rowIdx]
            for (colIdx in 1 until row.length) {
                if (isRowSymmetricalByIdx(row, colIdx)) {
                    symmetricalRowIndexes.add(colIdx)
                }
            }
            symmetricalIndexesInRows.add(symmetricalRowIndexes)
        }
        val overlappingRowIndexes = symmetricalIndexesInRows.reduce { acc, indexes -> acc.intersect(indexes) }.toList()


        val symmetricalIndexesInColumns = arrayListOf<Set<Int>>()
        for (colIdx in pattern[0].indices) {
            val column = pattern.map { it[colIdx] }.toList().joinToString("")

            val symmetricalColumnIndexes = hashSetOf<Int>()
            for (rowIdx in 1 until column.length) {
                if (isRowSymmetricalByIdx(column, rowIdx)) {
                    symmetricalColumnIndexes.add(rowIdx)
                }
            }
            symmetricalIndexesInColumns.add(symmetricalColumnIndexes)
        }
        val overlappingColumnIndexes = symmetricalIndexesInColumns.reduce { acc, indexes -> acc.intersect(indexes) }.toList()

        return ReflectionIndexes(
            overlappingRowIndexes,
            overlappingColumnIndexes
        )
    }

    fun findReflectionNumber(): Int {
        val ref = findReflection()
        if (ref.rows.size == 1) {
            return ref.rows[0]
        }
        return ref.columns[0] * 100
    }
}
