package day_14

data class Platform(
    val platform: List<String>
) {
    fun movedNorth(): Platform {
        val updatedPlatform = platform.toMutableList()
        for (colIdx in updatedPlatform[0].indices) {
            var moveBy = 0
            for (rowIdx in updatedPlatform.indices) {
                val char = updatedPlatform[rowIdx][colIdx]
                if (char == '.') {
                    moveBy++
                }
                if (char == 'O' && moveBy > 0) {
                    val currentRow = updatedPlatform[rowIdx]
                    val fallRow = updatedPlatform[rowIdx - moveBy]
                    updatedPlatform[rowIdx - moveBy] = "${fallRow.substring(0, colIdx)}O${fallRow.substring(colIdx + 1)}"
                    updatedPlatform[rowIdx] = "${currentRow.substring(0, colIdx)}.${currentRow.substring(colIdx + 1)}"
                }
                if (char == '#') {
                    moveBy = 0
                }
            }
        }
        return Platform(updatedPlatform)
    }

    fun countLoad(): Int {
        var load = 0
        for (rowIdx in platform.indices) {
            val countOfStones = platform[rowIdx].count { it == 'O' }
            load += (platform.size - rowIdx) * countOfStones
        }
        return load
    }

    fun movedWest(): Platform {
        val rotated = arrayListOf<String>()
        for (rowIdx in platform[0].indices) {
            var newRow = ""
            for (colIdx in platform.indices) {
                newRow += platform[colIdx][rowIdx]
            }
            rotated.add(newRow)
        }

        val moved = Platform(rotated).movedNorth()
        val rotated2 = arrayListOf<String>()
        for (rowIdx in moved.platform[0].indices) {
            var newRow = ""
            for (colIdx in moved.platform.indices) {
                newRow += moved.platform[colIdx][rowIdx]
            }
            rotated2.add(newRow)
        }
        return Platform(rotated2)
    }

    fun movedSouth(): Platform {
        val rotated = Platform(platform.reversed().toList())
        val moved = rotated.movedNorth()
        return Platform(moved.platform.reversed())
    }

    fun movedEast(): Platform {
        val rotated = Platform(platform.map { it.reversed() })
        val moved = rotated.movedWest()
        return Platform(moved.platform.map { it.reversed() })
    }

    fun moveCycles(cycles: Int): Platform {
        var currentCycle = this
        val cyclesHistory = arrayListOf<Platform>()
        var cycleLoopStart = -1
        var firstDuplicate: Platform? = null
        var lastCycle = cycles
        for (i in 1..cycles) {
            val newCycle = currentCycle.movedNorth().movedWest().movedSouth().movedEast()
            if (i == lastCycle) {
                return newCycle
            }
            currentCycle = newCycle
            if (cyclesHistory.contains(newCycle)) {
                if (firstDuplicate == newCycle) {
                    val loopSize = i - cycleLoopStart
                    do {
                        lastCycle -= loopSize
                    } while (lastCycle > i)
                    lastCycle += loopSize
                }

                if (cycleLoopStart == -1) {
                    cycleLoopStart = i
                    firstDuplicate = newCycle
                }
            }
            cyclesHistory.add(newCycle)
        }
        return currentCycle
    }

}
