package day_03

fun main() {
    val lines =
        object {}.javaClass.classLoader.getResourceAsStream("advent_of_code_2023_03.txt")?.bufferedReader()?.readLines()
            ?.stream()?.toList()!!

    val adjustedNumbers = getAdjustedNumbers(lines)
    val sum = adjustedNumbers.sum()
    val gerRatios = getGearRatios(lines)
    val sum2 = gerRatios.sum()

    println(sum)
    println(sum2)
}

fun getAdjustedNumbers(lines: List<String>): List<Int> {
    val numberPositions = getNumbersPositions(lines)
    val maxColumnIdx = lines[0].length - 1
    val adjustedNumbers = arrayListOf<Int>()

    numberPositions.forEach { numberPosition ->
        val lineStartIdx = (numberPosition.line - 1).coerceAtLeast(0)
        val columnStartIdx = (numberPosition.column - 1).coerceAtLeast(0)
        val lineEndIdx = (numberPosition.line + 1).coerceAtMost(lines.lastIndex)
        val columnEndIdx = (numberPosition.columnLastIdx() + 1).coerceAtMost(maxColumnIdx)
        for (lineIdx in lineStartIdx .. lineEndIdx) {
            var numberAdded = false
            for (columnIdx in columnStartIdx .. columnEndIdx) {
                val character = lines[lineIdx][columnIdx]
                if (!character.isDigit() && character != '.') {
                    adjustedNumbers.add(numberPosition.number)
                    numberAdded = true
                    break
                }
            }
            if (numberAdded) {
                break
            }
        }
    }

    return adjustedNumbers
}

fun getNumbersPositions(lines: List<String>): List<NumberPosition> {
    val positions = arrayListOf<NumberPosition>()
    val lineLength = lines[0].length
    for (lineId in lines.indices) {
        val line = lines[lineId]
        var numberSize = 0
        for (columnId in 0 .. lineLength) {
            val char = if(columnId == lineLength) '.' else line[columnId]       // ugly hack but works
            if (char.isDigit()) {
                numberSize++
            } else if (numberSize != 0) {
                val numberStart = columnId - numberSize
                val number = line.substring(numberStart, columnId).toInt()
                positions.add(NumberPosition(lineId, numberStart, numberSize, number))
                numberSize = 0
            }
        }
    }
    return positions
}

fun getGearPositions(lines: List<String>): List<Position> {
    val positions = arrayListOf<Position>()
    val lineLength = lines[0].length
    for (lineId in lines.indices) {
        val line = lines[lineId]
        for (columnId in 0 until  lineLength) {
            val char = line[columnId]
            if (char == '*') {
                positions.add(Position(lineId, columnId))
            }
        }
    }
    return positions
}

fun getGearRatios(lines: List<String>): List<Int> {
    val numberPositions = getNumbersPositions(lines)
    val maxColumnIdx = lines[0].length - 1
    val gearRatios = arrayListOf<Int>()
    val numberIndexes = hashMapOf<String, MutableList<Int>>()
    val gearPositions = getGearPositions(lines)

    numberPositions.forEach { numberPosition ->
        val lineStartIdx = (numberPosition.line - 1).coerceAtLeast(0)
        val columnStartIdx = (numberPosition.column - 1).coerceAtLeast(0)
        val lineEndIdx = (numberPosition.line + 1).coerceAtMost(lines.lastIndex)
        val columnEndIdx = (numberPosition.columnLastIdx() + 1).coerceAtMost(maxColumnIdx)
        for (lineIdx in lineStartIdx .. lineEndIdx) {
            for (columnIdx in columnStartIdx .. columnEndIdx) {
                val idx = "$lineIdx.$columnIdx"
                if (numberIndexes.containsKey(idx)) {
                    numberIndexes[idx]!!.add(numberPosition.number)
                } else {
                    numberIndexes[idx] = arrayListOf(numberPosition.number)
                }
            }
        }
    }

    gearPositions.forEach { gearPosition ->
        val idx = "${gearPosition.lineIdx}.${gearPosition.columnIdx}"
        if (numberIndexes.containsKey(idx)) {
            if (numberIndexes[idx]!!.size == 2) {
                val gearStrength = numberIndexes[idx]!![0] * numberIndexes[idx]!![1]
                gearRatios.add(gearStrength)
            }
        }
    }

    return gearRatios
}