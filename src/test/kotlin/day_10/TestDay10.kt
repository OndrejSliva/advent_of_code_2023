package day_10

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class TestDay10 {

    private val map1 = listOf(
        ".....",
        ".S-7.",
        ".|.|.",
        ".L-J.",
        ".....",
    )

    private val map2 = listOf(
        "-L|F7",
        "7S-7|",
        "L|7||",
        "-L-J|",
        "L|-JF",
    )

    private val map3 = listOf(
        "..F7.",
        ".FJ|.",
        "SJ.L7",
        "|F--J",
        "LJ...",
    )

    private val map4 = listOf(
        "...........",
        ".S-------7.",
        ".|F-----7|.",
        ".||.....||.",
        ".||.....||.",
        ".|L-7.F-J|.",
        ".|..|.|..|.",
        ".L--J.L--J.",
        "...........",
    )

    private val map5 = listOf(
        ".F----7F7F7F7F-7....",
        ".|F--7||||||||FJ....",
        ".||.FJ||||||||L7....",
        "FJL7L7LJLJ||LJ.L-7..",
        "L--J.L7...LJS7F-7L7.",
        "....F-J..F7FJ|L7L7L7",
        "....L7.F7||L7|.L7L7|",
        ".....|FJLJ|FJ|F7|.LJ",
        "....FJL-7.||.||||...",
        "....L---J.LJ.LJLJ..."
    )

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetStartIdx {
        @ParameterizedTest
        @MethodSource("mapsAndStarts")
        fun `it should find startIdx`(map: List<String>, expectedStart: MapIndex) {
            val start = getStartIdx(map)
            assertEquals(expectedStart, start)
        }

        private fun mapsAndStarts() = listOf(
            Arguments.of(map1, MapIndex(1, 1)),
            Arguments.of(map2, MapIndex(1, 1)),
            Arguments.of(map3, MapIndex(2, 0)),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetPossibleMoves {

        @ParameterizedTest
        @MethodSource("mapsAndSPositions")
        fun `it should get possible moves`(map: List<String>, position: MapIndex, expectedPossibleMoves: List<MapIndex>) {
            val possibleMoves = getPossibleMoves(map, position)
            assertEquals(expectedPossibleMoves.toSet(), possibleMoves.toSet())
        }

        private fun mapsAndSPositions() = listOf(
            Arguments.of(map3, MapIndex(0, 2), listOf(MapIndex(1, 2), MapIndex(0, 3))),
            Arguments.of(map3, MapIndex(0, 3), listOf(MapIndex(1, 3), MapIndex(0, 2))),
            Arguments.of(map3, MapIndex(1, 3), listOf(MapIndex(0, 3), MapIndex(2, 3))),
            Arguments.of(map3, MapIndex(2, 3), listOf(MapIndex(1, 3), MapIndex(2, 4))),
            Arguments.of(map3, MapIndex(3, 4), listOf(MapIndex(2, 4), MapIndex(3, 3))),
            Arguments.of(map3, MapIndex(3, 3), listOf(MapIndex(3, 2), MapIndex(3, 4))),
            Arguments.of(map1, MapIndex(1, 1), listOf(MapIndex(1, 2), MapIndex(2, 1))),
            Arguments.of(map2, MapIndex(1, 1), listOf(MapIndex(1, 2), MapIndex(2, 1))),
            Arguments.of(map2, MapIndex(1, 2), listOf(MapIndex(1, 1), MapIndex(1, 3))),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetNextMove {

        @ParameterizedTest
        @MethodSource("mapsAndSPositions")
        fun `it should get next move`(map: List<String>, currentPosition: MapIndex, lastPosition: MapIndex, expectedNextMove: MapIndex) {
            val nextMove = getNextMove(map, currentPosition, lastPosition)
            assertEquals(expectedNextMove, nextMove)
        }

        private fun mapsAndSPositions() = listOf(
            Arguments.of(map3, MapIndex(0, 2), MapIndex(1, 2), MapIndex(0, 3)),
            Arguments.of(map3, MapIndex(0, 3), MapIndex(1, 3), MapIndex(0, 2)),
            Arguments.of(map3, MapIndex(1, 3), MapIndex(0, 3), MapIndex(2, 3)),
            Arguments.of(map3, MapIndex(2, 3), MapIndex(1, 3), MapIndex(2, 4)),
            Arguments.of(map3, MapIndex(3, 4), MapIndex(2, 4), MapIndex(3, 3)),
            Arguments.of(map3, MapIndex(3, 3), MapIndex(3, 2), MapIndex(3, 4)),
            Arguments.of(map1, MapIndex(1, 1), MapIndex(1, 2), MapIndex(2, 1)),
            Arguments.of(map2, MapIndex(1, 1), MapIndex(1, 2), MapIndex(2, 1)),
            Arguments.of(map1, MapIndex(3, 3), MapIndex(2, 3), MapIndex(3, 2)),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetLoopSize {


        @ParameterizedTest
        @MethodSource("mapsAndLoopSizes")
        fun `it should get loop size`(map: List<String>, expectedLoopSize: Int) {
            val loopSize = getLoopSize(map)
            assertEquals(expectedLoopSize, loopSize)
        }

        private fun mapsAndLoopSizes() = listOf(
            Arguments.of(map1, 8),
            Arguments.of(map2, 8),
            Arguments.of(map3, 16),
            Arguments.of(map4, 46),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetLoopPath {

        @ParameterizedTest
        @MethodSource("mapsAndBoundaries")
        fun `it should get loop path`(map: List<String>, expectedLoopPath: List<MapIndex>) {
            val loopPath = getLoopPath(map)
            assertEquals(expectedLoopPath, loopPath)
        }

        private fun mapsAndBoundaries(): List<Arguments> {
            val expectedPath = listOf(
                MapIndex(1, 1), MapIndex(2, 1), MapIndex(3, 1), MapIndex(3, 2),
                MapIndex(3, 3), MapIndex(2, 3), MapIndex(1, 3), MapIndex(1, 2)
            )
            return listOf(
                Arguments.of(map1, expectedPath),
                Arguments.of(map2, expectedPath),
            )
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class CountInnerIndexesUpscaleMap {

        @ParameterizedTest
        @MethodSource("mapsAndInnerIndexesCounts")
        fun `it should count inner`(map: List<String>, expectedInnerCount: Int) {
            val innerCount = countInnerIndexesUpscaleMap(map)
            assertEquals(expectedInnerCount, innerCount)
        }

        private fun mapsAndInnerIndexesCounts() = listOf(
            Arguments.of(map1, 1),
            Arguments.of(map2, 1),
            Arguments.of(map3, 1),
            Arguments.of(map4, 4),
            Arguments.of(map5, 8),
        )
    }

    @Nested
    inner class TestMapIndex {

        @Test
        fun `it should get left idx`() {
            val mapIdx = MapIndex(1, 1)
            val leftIdx = MapIndex(1, 0)
            assertEquals(leftIdx, mapIdx.left())
        }

        @Test
        fun `it should get bottom idx`() {
            val mapIdx = MapIndex(1, 1)
            val bottomIdx = MapIndex(2, 1)
            assertEquals(bottomIdx, mapIdx.bottom())
        }

        @Test
        fun `it should get right idx`() {
            val mapIdx = MapIndex(1, 1)
            val rightIdx = MapIndex(1, 2)
            assertEquals(rightIdx, mapIdx.right())
        }

        @Test
        fun `it should get top idx`() {
            val mapIdx = MapIndex(1, 1)
            val topIdx = MapIndex(0, 1)
            assertEquals(topIdx, mapIdx.top())
        }
    }
}