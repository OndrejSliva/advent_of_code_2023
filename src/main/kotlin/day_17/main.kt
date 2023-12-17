package day_17

import java.util.Stack

fun main() {
    val map =
        object {}.javaClass.classLoader.getResourceAsStream("advent_of_code_2023_17.txt")?.bufferedReader()?.readLines()
            ?.stream()?.toList()!!

    val shortestPath = findShortestPathLength(map, ::getNextPossibleDirections, ::alwaysCanStop, 3)
    println(shortestPath)
    val shortestPathBig = findShortestPathLength(map, ::getNextPossibleDirectionsBig, ::bigCanStop, 10)
    println(shortestPathBig)
}

fun alwaysCanStop(stepsInCurrentDirection: Int): Boolean = true
fun bigCanStop(stepsInCurrentDirection: Int): Boolean = stepsInCurrentDirection in 4..10

fun findShortestPathLength(
    map: List<String>,
    getNextDirectionsFunction: (currentDirection: Direction, stepsInCurrentDirection: Int) -> Set<Direction>,
    canStopFunction: (stepsInCurrentDirection: Int) -> Boolean,
    maxStepsInDirection: Int
): Int {
    val stack: Stack<Step> = Stack()
    val startingStep = Step(0, 0, Direction.LEFT, 0, 0)
    stack.push(startingStep.right(0))
    stack.push(startingStep.down(0))

    val mapHeight = map.size
    val mapWidth = map[0].length
    var minDistance: Int = (map[0].sumOf { it.digitToInt() } + map.indices.sumOf { map[0][it].digitToInt() }) * 2

    val directions = listOf(Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.DOWN)
    val minAtPositionMap: MutableMap<Int, MutableMap<Int, MutableMap<Direction, MutableMap<Int, Int>>>> = map.indices.associateWith { rowIdx ->
        map[rowIdx].indices.associateWith { colIdx ->
            directions.associateWith { direction ->
                (0..maxStepsInDirection).associateWith { stepIdx ->
                    minDistance
                }.toMutableMap()
            }.toMutableMap()
        }.toMutableMap()
    }.toMutableMap()

    while (stack.isNotEmpty()) {
        val currentStep = stack.pop()
        if (isOut(currentStep, mapWidth, mapHeight)) {
            continue
        }

        val minAtPosition = minAtPositionMap.getValue(currentStep.currentRowIdx)
            .getValue(currentStep.currentColumnIdx)
            .getValue(currentStep.currentDirection)
            .getValue(currentStep.stepsInCurrentDirection)

        if (currentStep.currentDistance < minAtPosition) {
            minAtPositionMap.getValue(currentStep.currentRowIdx)
                .getValue(currentStep.currentColumnIdx)
                .getValue(currentStep.currentDirection)[currentStep.stepsInCurrentDirection]  = currentStep.currentDistance
        } else {
            continue
        }

        val currentBlockDistance = map[currentStep.currentRowIdx][currentStep.currentColumnIdx].digitToInt()
        if (currentStep.currentRowIdx == map.lastIndex && currentStep.currentColumnIdx == map[0].lastIndex && canStopFunction(currentStep.stepsInCurrentDirection)) {
            val newDistance = currentBlockDistance + currentStep.currentDistance
            if (newDistance < minDistance) {
                minDistance = newDistance
            }
            continue
        }

        if (currentStep.currentDistance + currentBlockDistance >= minDistance) {
            continue
        }

        val nextDirections = getNextDirectionsFunction(currentStep.currentDirection, currentStep.stepsInCurrentDirection)
        if (nextDirections.contains(Direction.DOWN)) {
            stack.push(currentStep.down(currentBlockDistance))
        }
        if (nextDirections.contains(Direction.UP)) {
            stack.push(currentStep.up(currentBlockDistance))
        }
        if (nextDirections.contains(Direction.LEFT)) {
            stack.push(currentStep.left(currentBlockDistance))
        }
        if (nextDirections.contains(Direction.RIGHT)) {
            stack.push(currentStep.right(currentBlockDistance))
        }
    }

    return minDistance
}

fun getNextPossibleDirections(currentDirection: Direction, stepsInCurrentDirection: Int): Set<Direction> = when(currentDirection) {
    Direction.DOWN -> if (stepsInCurrentDirection >= 3) setOf(Direction.RIGHT, Direction.LEFT) else setOf(Direction.DOWN, Direction.RIGHT, Direction.LEFT)
    Direction.UP -> if (stepsInCurrentDirection >= 3) setOf(Direction.RIGHT, Direction.LEFT) else setOf(Direction.RIGHT, Direction.LEFT, Direction.UP)
    Direction.LEFT -> if (stepsInCurrentDirection >= 3) setOf(Direction.DOWN, Direction.UP) else setOf(Direction.DOWN, Direction.UP, Direction.LEFT)
    Direction.RIGHT -> if (stepsInCurrentDirection >= 3) setOf(Direction.DOWN, Direction.UP) else setOf(Direction.RIGHT, Direction.DOWN, Direction.UP)
}

fun getNextPossibleDirectionsBig(currentDirection: Direction, stepsInCurrentDirection: Int): Set<Direction> = when(currentDirection) {
    Direction.DOWN -> if (stepsInCurrentDirection < 4) setOf(Direction.DOWN) else if (stepsInCurrentDirection < 10) setOf(Direction.DOWN, Direction.RIGHT, Direction.LEFT) else setOf(Direction.RIGHT, Direction.LEFT)
    Direction.UP -> if (stepsInCurrentDirection < 4) setOf(Direction.UP) else if (stepsInCurrentDirection < 10) setOf(Direction.RIGHT, Direction.LEFT, Direction.UP) else setOf(Direction.RIGHT, Direction.LEFT)
    Direction.LEFT -> if (stepsInCurrentDirection < 4) setOf(Direction.LEFT) else if (stepsInCurrentDirection < 10) setOf(Direction.DOWN, Direction.UP, Direction.LEFT) else setOf(Direction.DOWN, Direction.UP)
    Direction.RIGHT -> if (stepsInCurrentDirection < 4) setOf(Direction.RIGHT) else if (stepsInCurrentDirection < 10) setOf(Direction.RIGHT, Direction.DOWN, Direction.UP) else setOf(Direction.DOWN, Direction.UP)
}

fun isOut(currentStep: Step, width: Int, height: Int): Boolean {
    return currentStep.currentRowIdx < 0 || currentStep.currentColumnIdx < 0 || currentStep.currentRowIdx >= height || currentStep.currentColumnIdx >= width
}