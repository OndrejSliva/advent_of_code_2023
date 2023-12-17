package day_17

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class TestDay17 {

    private val map = listOf(
        "2413432311323",
        "3215453535623",
        "3255245654254",
        "3446585845452",
        "4546657867536",
        "1438598798454",
        "4457876987766",
        "3637877979653",
        "4654967986887",
        "4564679986453",
        "1224686865563",
        "2546548887735",
        "4322674655533",
    )

    @Nested
    inner class FindShortestPathLength {
        @Test
        fun `it should find shortest path with getNextPossibleDirections`() {
            val shortestPath = findShortestPathLength(map, ::getNextPossibleDirections, ::alwaysCanStop, 3)
            assertEquals(102, shortestPath)
        }

        @Test
        fun `it should find shortest path with getNextPossibleDirectionsBig`() {
            val shortestPath = findShortestPathLength(map, ::getNextPossibleDirectionsBig, ::bigCanStop, 10)
            assertEquals(94, shortestPath)
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetNextPossibleDirections {

        @ParameterizedTest
        @MethodSource("directionsAndNextDirections")
        fun `it should get next possible directions`(currentDirection: Direction, stepsInCurrentDirection: Int, expectedDirections: Set<Direction>) {
            val nextDirections = getNextPossibleDirections(currentDirection, stepsInCurrentDirection)
            assertEquals(expectedDirections, nextDirections)
        }

        private fun directionsAndNextDirections() = listOf(
            Arguments.of(Direction.LEFT, 0, setOf(Direction.LEFT, Direction.DOWN, Direction.UP)),
            Arguments.of(Direction.LEFT, 1, setOf(Direction.LEFT, Direction.DOWN, Direction.UP)),
            Arguments.of(Direction.LEFT, 2, setOf(Direction.LEFT, Direction.DOWN, Direction.UP)),
            Arguments.of(Direction.LEFT, 3, setOf(Direction.DOWN, Direction.UP)),

            Arguments.of(Direction.RIGHT, 0, setOf(Direction.RIGHT, Direction.DOWN, Direction.UP)),
            Arguments.of(Direction.RIGHT, 1, setOf(Direction.RIGHT, Direction.DOWN, Direction.UP)),
            Arguments.of(Direction.RIGHT, 2, setOf(Direction.RIGHT, Direction.DOWN, Direction.UP)),
            Arguments.of(Direction.RIGHT, 3, setOf(Direction.DOWN, Direction.UP)),

            Arguments.of(Direction.UP, 0, setOf(Direction.UP, Direction.RIGHT, Direction.LEFT)),
            Arguments.of(Direction.UP, 1, setOf(Direction.UP, Direction.RIGHT, Direction.LEFT)),
            Arguments.of(Direction.UP, 2, setOf(Direction.UP, Direction.RIGHT, Direction.LEFT)),
            Arguments.of(Direction.UP, 3, setOf(Direction.RIGHT, Direction.LEFT)),

            Arguments.of(Direction.DOWN, 0, setOf(Direction.DOWN, Direction.RIGHT, Direction.LEFT)),
            Arguments.of(Direction.DOWN, 1, setOf(Direction.DOWN, Direction.RIGHT, Direction.LEFT)),
            Arguments.of(Direction.DOWN, 2, setOf(Direction.DOWN, Direction.RIGHT, Direction.LEFT)),
            Arguments.of(Direction.DOWN, 3, setOf(Direction.RIGHT, Direction.LEFT)),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetNextPossibleDirectionsBig {

        @ParameterizedTest
        @MethodSource("directionsAndNextDirections")
        fun `it should get next possible directions`(currentDirection: Direction, stepsInCurrentDirection: Int, expectedDirections: Set<Direction>) {
            val nextDirections = getNextPossibleDirectionsBig(currentDirection, stepsInCurrentDirection)
            assertEquals(expectedDirections, nextDirections)
        }

        private fun directionsAndNextDirections() = listOf(
            Arguments.of(Direction.LEFT, 0, setOf(Direction.LEFT)),
            Arguments.of(Direction.LEFT, 1, setOf(Direction.LEFT)),
            Arguments.of(Direction.LEFT, 2, setOf(Direction.LEFT)),
            Arguments.of(Direction.LEFT, 3, setOf(Direction.LEFT)),
            Arguments.of(Direction.LEFT, 4, setOf(Direction.LEFT, Direction.DOWN, Direction.UP)),
            Arguments.of(Direction.LEFT, 10, setOf(Direction.DOWN, Direction.UP)),

            Arguments.of(Direction.RIGHT, 0, setOf(Direction.RIGHT)),
            Arguments.of(Direction.RIGHT, 1, setOf(Direction.RIGHT)),
            Arguments.of(Direction.RIGHT, 2, setOf(Direction.RIGHT)),
            Arguments.of(Direction.RIGHT, 3, setOf(Direction.RIGHT)),
            Arguments.of(Direction.RIGHT, 4, setOf(Direction.RIGHT, Direction.DOWN, Direction.UP)),
            Arguments.of(Direction.RIGHT, 10, setOf(Direction.DOWN, Direction.UP)),

            Arguments.of(Direction.UP, 0, setOf(Direction.UP)),
            Arguments.of(Direction.UP, 1, setOf(Direction.UP)),
            Arguments.of(Direction.UP, 2, setOf(Direction.UP)),
            Arguments.of(Direction.UP, 3, setOf(Direction.UP)),
            Arguments.of(Direction.UP, 4, setOf(Direction.UP, Direction.RIGHT, Direction.LEFT)),
            Arguments.of(Direction.UP, 10, setOf(Direction.RIGHT, Direction.LEFT)),

            Arguments.of(Direction.DOWN, 0, setOf(Direction.DOWN)),
            Arguments.of(Direction.DOWN, 1, setOf(Direction.DOWN)),
            Arguments.of(Direction.DOWN, 2, setOf(Direction.DOWN)),
            Arguments.of(Direction.DOWN, 3, setOf(Direction.DOWN)),
            Arguments.of(Direction.DOWN, 4, setOf(Direction.DOWN, Direction.RIGHT, Direction.LEFT)),
            Arguments.of(Direction.DOWN, 10, setOf(Direction.RIGHT, Direction.LEFT)),
        )
    }
}