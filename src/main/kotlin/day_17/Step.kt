package day_17

data class Step(
    val currentRowIdx: Int,
    val currentColumnIdx: Int,
    val currentDirection: Direction,
    val stepsInCurrentDirection: Int,
    val currentDistance: Int,
) {
    fun left(distance: Int): Step {
        val stepsInDirection = if (currentDirection == Direction.LEFT) stepsInCurrentDirection + 1 else 1
        return Step(currentRowIdx, currentColumnIdx - 1, Direction.LEFT, stepsInDirection, currentDistance + distance)
    }

    fun right(distance: Int): Step {
        val stepsInDirection = if (currentDirection == Direction.RIGHT) stepsInCurrentDirection + 1 else 1
        return Step(currentRowIdx, currentColumnIdx + 1, Direction.RIGHT, stepsInDirection, currentDistance + distance)
    }

    fun up(distance: Int): Step {
        val stepsInDirection = if (currentDirection == Direction.UP) stepsInCurrentDirection + 1 else 1
        return Step(currentRowIdx - 1, currentColumnIdx, Direction.UP, stepsInDirection, currentDistance + distance)
    }

    fun down(distance: Int): Step {
        val stepsInDirection = if (currentDirection == Direction.DOWN) stepsInCurrentDirection + 1 else 1
        return Step(currentRowIdx + 1, currentColumnIdx, Direction.DOWN, stepsInDirection, currentDistance + distance)
    }
}
