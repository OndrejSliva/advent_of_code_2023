package day_14

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class TestDay14 {

    private val platform = Platform(
        listOf(
            "O....#....",
            "O.OO#....#",
            ".....##...",
            "OO.#O....O",
            ".O.....O#.",
            "O.#..O.#.#",
            "..O..#O..O",
            ".......O..",
            "#....###..",
            "#OO..#....",
        )
    )

    private val platformMovedToNorth = Platform(
        listOf(
            "OOOO.#.O..",
            "OO..#....#",
            "OO..O##..O",
            "O..#.OO...",
            "........#.",
            "..#....#.#",
            "..O..#.O.O",
            "..O.......",
            "#....###..",
            "#....#....",
        )
    )

    private val platformMovedToWest = Platform(
        listOf(
            "O....#....",
            "OOO.#....#",
            ".....##...",
            "OO.#OO....",
            "OO......#.",
            "O.#O...#.#",
            "O....#OO..",
            "O.........",
            "#....###..",
            "#OO..#....",
        )
    )

    private val platformMovedToSouth = Platform(
        listOf(
            ".....#....",
            "....#....#",
            "...O.##...",
            "...#......",
            "O.O....O#O",
            "O.#..O.#.#",
            "O....#....",
            "OO....OO..",
            "#OO..###..",
            "#OO.O#...O",
        )
    )

    private val platformMovedToEast = Platform(
        listOf(
            "....O#....",
            ".OOO#....#",
            ".....##...",
            ".OO#....OO",
            "......OO#.",
            ".O#...O#.#",
            "....O#..OO",
            ".........O",
            "#....###..",
            "#..OO#....",
        )
    )

    private val platformAfterOneCycle = Platform(
        listOf(
            ".....#....",
            "....#...O#",
            "...OO##...",
            ".OO#......",
            ".....OOO#.",
            ".O#...O#.#",
            "....O#....",
            "......OOOO",
            "#...O###..",
            "#..OO#....",
        )
    )

    private val platformAfterTwoCycles = Platform(
        listOf(
            ".....#....",
            "....#...O#",
            ".....##...",
            "..O#......",
            ".....OOO#.",
            ".O#...O#.#",
            "....O#...O",
            ".......OOO",
            "#..OO###..",
            "#.OOO#...O",
        )
    )

    private val platformAfterThreeCycles = Platform(
        listOf(
            ".....#....",
            "....#...O#",
            ".....##...",
            "..O#......",
            ".....OOO#.",
            ".O#...O#.#",
            "....O#...O",
            ".......OOO",
            "#...O###.O",
            "#.OOO#...O",
        )
    )

    @Nested
    inner class PlatformMovedNorth {
        @Test
        fun `it should move movable stones north`() {
            val movedPlatform = platform.movedNorth()
            assertEquals(platformMovedToNorth, movedPlatform)
        }
    }

    @Nested
    inner class PlatformCountLoad {
        @Test
        fun `it should move movable stones north`() {
            val load = platformMovedToNorth.countLoad()
            assertEquals(136, load)
        }
    }

    @Nested
    inner class PlatformMovedWest {
        @Test
        fun `it should move movable stones west`() {
            val movedPlatform = platform.movedWest()
            assertEquals(platformMovedToWest, movedPlatform)
        }
    }

    @Nested
    inner class PlatformMovedSouth {
        @Test
        fun `it should move movable stones west`() {
            val movedPlatform = platform.movedSouth()
            assertEquals(platformMovedToSouth, movedPlatform)
        }
    }

    @Nested
    inner class PlatformMovedEast {
        @Test
        fun `it should move movable stones east`() {
            val movedPlatform = platform.movedEast()
            assertEquals(platformMovedToEast, movedPlatform)
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PlatformMovedCycle {
        @ParameterizedTest
        @MethodSource("cyclesAndResults")
        fun `it should move movable stones east`(cycles: Int, expectedPlatform: Platform) {
            val movedPlatform = platform.moveCycles(cycles)
            assertEquals(expectedPlatform, movedPlatform)
        }

        private fun cyclesAndResults() = listOf(
            Arguments.of(1, platformAfterOneCycle),
            Arguments.of(2, platformAfterTwoCycles),
            Arguments.of(3, platformAfterThreeCycles),
        )
    }

    @Nested
    inner class PlatformCountPowerAfterCycles {
        @Test
        fun `it should count power after 1000000000 cycles`() {
            val movedCycle = platform.moveCycles(1000000000)
            assertEquals(64, movedCycle.countLoad())
        }
    }

}