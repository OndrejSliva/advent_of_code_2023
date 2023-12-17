package day_10

fun main() {
    val lines =
        object {}.javaClass.classLoader.getResourceAsStream("advent_of_code_2023_10.txt")?.bufferedReader()?.readLines()
            ?.stream()?.toList()!!
    val loopSize = getLoopSize(lines)
    val longestDistance = loopSize / 2
    println(longestDistance)
    println(countInnerIndexesUpscaleMap(lines))
}

fun getStartIdx(map: List<String>): MapIndex {
    val rowIdx = getRowIdx(map)
    val colIdx = map[rowIdx].indexOf('S')
    return MapIndex(rowIdx, colIdx)
}

private fun getRowIdx(map: List<String>): Int {
    for (i in map.indices) {
        if (map[i].contains('S')) {
            return i
        }
    }
    throw Exception("Start not found")
}

fun getPossibleMoves(map: List<String>, position: MapIndex) = when (val currentPipe = map[position.rowIdx][position.colIdx]) {
    '|' -> listOf(position.top(), position.bottom())
    '-' -> listOf(position.left(), position.right())
    'L' -> listOf(position.top(), position.right())
    'J' -> listOf(position.left(), position.top())
    '7' -> listOf(position.left(), position.bottom())
    'F' -> listOf(position.bottom(), position.right())
    'S' -> getPossibleMovesForStartPosition(map, position)
    else -> throw Exception("Cannot get possible moves for $currentPipe")
}

val possibleLeft = listOf('-', 'L', 'F', 'S')
val possibleBottom = listOf('|', 'L', 'J', 'S')
val possibleRight = listOf('-', 'J', '7', 'S')
val possibleTop = listOf('|', '7', 'F', 'S')

fun getPossibleMovesForStartPosition(map: List<String>, position: MapIndex): List<MapIndex> {
    val possibleMoves = arrayListOf<MapIndex>()
    val mapWidth = map[0].length
    val mapHeight = map.size
    val leftMapIdx = position.left()
    if (!leftMapIdx.isOut(mapWidth, mapHeight)) {
        val left = map[leftMapIdx.rowIdx][leftMapIdx.colIdx]
        if (possibleLeft.contains(left)) {
            possibleMoves.add(leftMapIdx)
        }
    }

    val bottomMapIdx = position.bottom()
    if (!bottomMapIdx.isOut(mapWidth, mapHeight)) {
        val bottom = map[bottomMapIdx.rowIdx][bottomMapIdx.colIdx]
        if (possibleBottom.contains(bottom)) {
            possibleMoves.add(bottomMapIdx)
        }

    }

    val rightMapIdx = position.right()
    if (!rightMapIdx.isOut(mapWidth, mapHeight)) {
        val right = map[rightMapIdx.rowIdx][rightMapIdx.colIdx]
        if (possibleRight.contains(right)) {
            possibleMoves.add(rightMapIdx)
        }
    }

    val topMapIdx = position.top()
    if (!topMapIdx.isOut(mapWidth, mapHeight)) {
        val top = map[topMapIdx.rowIdx][topMapIdx.colIdx]
        if (possibleTop.contains(top)) {
            possibleMoves.add(topMapIdx)
        }
    }

    return possibleMoves
}

fun getNextMove(map: List<String>, currentPosition: MapIndex, lastPosition: MapIndex): MapIndex {
    val possibleMoves = getPossibleMoves(map, currentPosition)
    return if(possibleMoves[0] == lastPosition) possibleMoves[1] else possibleMoves[0]
}

fun getLoopSize(map: List<String>): Int = getLoopPath(map).size

fun getLoopPath(map: List<String>): List<MapIndex> {
    val startPosition = getStartIdx(map)
    val path = arrayListOf(startPosition)

    var lastPosition = startPosition
    var currentPosition = getPossibleMoves(map, startPosition)[0]

    while (currentPosition != startPosition) {
        path.add(currentPosition)
        val nextPosition = getNextMove(map, currentPosition, lastPosition)
        lastPosition = currentPosition
        currentPosition = nextPosition
    }
    return path
}

enum class CurrentState {
    OUTSIDE, UNKNOWN
}

fun getInnerIndexes(map: List<String>): Set<MapIndex> {
    val path = getLoopPath(map)
    val foundInnerIndexes = hashSetOf<MapIndex>()
    val mapWidth = map[0].length
    val mapHeight = map.size
    val foundOutIndexes = hashSetOf<MapIndex>()
    for (rowIdx in map.indices) {
        for (colIdx in map[0].indices) {
            val currentPosition = MapIndex(rowIdx, colIdx)
            if (path.contains(currentPosition) || foundInnerIndexes.contains(currentPosition) || foundOutIndexes.contains(currentPosition)) {
                continue
            }
            val foundIndexes = getInnerIndexesQueue(path.toSet(), currentPosition,  mapWidth, mapHeight, foundOutIndexes)
            foundInnerIndexes.addAll(foundIndexes)
        }
    }

    return foundInnerIndexes
}

private fun getInnerIndexesQueue(
    loopPath: Set<MapIndex>,
    startIndex: MapIndex,
    mapWidth: Int,
    mapHeight: Int,
    outIndexes: MutableSet<MapIndex>
): Set<MapIndex> {
    var currentState = CurrentState.UNKNOWN
    val toVisitList = arrayListOf(startIndex)
    val visited = hashSetOf<MapIndex>()

    while (toVisitList.isNotEmpty()) {
        val actualVisit = toVisitList.removeAt(0)
        visited.add(actualVisit)

        val left = actualVisit.left()
        if (left.isOut(mapWidth, mapHeight)) {
            currentState = CurrentState.OUTSIDE
        } else if (!visited.contains(left) && !loopPath.contains(left) && !toVisitList.contains(left)) {
            toVisitList.add(left)
        }
        val right = actualVisit.right()
        if (right.isOut(mapWidth, mapHeight)) {
            currentState = CurrentState.OUTSIDE
        } else if (!visited.contains(right) && !loopPath.contains(right) && !toVisitList.contains(right)) {
            toVisitList.add(right)
        }
        val top = actualVisit.top()
        if (top.isOut(mapWidth, mapHeight)) {
            currentState = CurrentState.OUTSIDE
        } else if (!visited.contains(top) && !loopPath.contains(top) && !toVisitList.contains(top)) {
            toVisitList.add(top)
        }
        val bottom = actualVisit.bottom()
        if (bottom.isOut(mapWidth, mapHeight)) {
            currentState = CurrentState.OUTSIDE
        } else if (!visited.contains(bottom) && !loopPath.contains(bottom) && !toVisitList.contains(bottom)) {
            toVisitList.add(bottom)
        }

        val leftTop = actualVisit.leftTop()
        if (leftTop.isOut(mapWidth, mapHeight)) {
            currentState = CurrentState.OUTSIDE
        } else if (!visited.contains(leftTop) && !loopPath.contains(leftTop) && !toVisitList.contains(leftTop)) {
            toVisitList.add(leftTop)
        }
        val leftBottom = actualVisit.leftBottom()
        if (leftBottom.isOut(mapWidth, mapHeight)) {
            currentState = CurrentState.OUTSIDE
        } else if (!visited.contains(leftBottom) && !loopPath.contains(leftBottom) && !toVisitList.contains(leftBottom)) {
            toVisitList.add(leftBottom)
        }
        val rightTop = actualVisit.rightTop()
        if (rightTop.isOut(mapWidth, mapHeight)) {
            currentState = CurrentState.OUTSIDE
        } else if (!visited.contains(rightTop) && !loopPath.contains(rightTop) && !toVisitList.contains(rightTop)) {
            toVisitList.add(rightTop)
        }
        val rightBottom = actualVisit.rightBottom()
        if (rightBottom.isOut(mapWidth, mapHeight)) {
            currentState = CurrentState.OUTSIDE
        } else if (!visited.contains(rightBottom) && !loopPath.contains(rightBottom) && !toVisitList.contains(rightBottom)) {
            toVisitList.add(rightBottom)
        }
    }

    return if (currentState == CurrentState.UNKNOWN) {
        visited
    } else {
        outIndexes.addAll(visited)
        setOf()
    }
}

// very ugly but works
fun countInnerIndexesUpscaleMap(map: List<String>): Int {
    val newMap = arrayListOf<String>()
    map.forEach {
        var newRow = ""
        it.forEach { currentChar ->
            newRow += "$currentChar."
        }

        newMap.add(newRow)
        newMap.add(".".repeat(map[0].length * 2))
    }

    val path = getLoopPath(map)
    for (i in 0 until path.size - 1) {
        val currentIdx = MapIndex(path[i].rowIdx * 2, path[i].colIdx * 2)
        val nextIdx = MapIndex(path[i + 1].rowIdx * 2, path[i + 1].colIdx * 2)
        if (currentIdx.colIdx == nextIdx.colIdx) {
            val rowIdx = (currentIdx.rowIdx + nextIdx.rowIdx) / 2
            val l = newMap[rowIdx].toCharArray()
            l[currentIdx.colIdx] = '|'
            newMap[rowIdx] = String(l)
        } else if (currentIdx.rowIdx == nextIdx.rowIdx) {
            val colIdx = (currentIdx.colIdx + nextIdx.colIdx) / 2
            val l = newMap[currentIdx.rowIdx].toCharArray()
            l[colIdx] = '-'
            newMap[currentIdx.rowIdx] = String(l)
        } else {
            throw Exception("Should not happen")
        }
    }

    val currentIdx = MapIndex(path[0].rowIdx * 2, path[0].colIdx * 2)
    val nextIdx = MapIndex(path[path.lastIndex].rowIdx * 2, path[path.lastIndex].colIdx * 2)
    if (currentIdx.colIdx == nextIdx.colIdx) {
        val rowIdx = (currentIdx.rowIdx + nextIdx.rowIdx) / 2
        val l = newMap[rowIdx].toCharArray()
        l[currentIdx.colIdx] = '|'
        newMap[rowIdx] = String(l)
    } else if (currentIdx.rowIdx == nextIdx.rowIdx) {
        val colIdx = (currentIdx.colIdx + nextIdx.colIdx) / 2
        val l = newMap[currentIdx.rowIdx].toCharArray()
        l[colIdx] = '-'
        newMap[currentIdx.rowIdx] = String(l)
    }


    val indexes = getInnerIndexes(newMap).filter { it.rowIdx % 2 == 0 && it.colIdx % 2 == 0 }.map { MapIndex(it.rowIdx / 2, it.colIdx / 2) }
    return indexes.size
}