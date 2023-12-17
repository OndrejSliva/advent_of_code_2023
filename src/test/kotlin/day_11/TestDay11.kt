package day_11

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class TestDay11 {

    private val smallGalaxy = listOf(
        "...#......",
        ".......#..",
        "#.........",
        "..........",
        "......#...",
        ".#........",
        ".........#",
        "..........",
        ".......#..",
        "#...#.....",
    )

    private val expandedGalaxy = listOf(
        "....#........",
        ".........#...",
        "#............",
        ".............",
        ".............",
        "........#....",
        ".#...........",
        "............#",
        ".............",
        ".............",
        ".........#...",
        "#....#.......",
    )

    private val expandedGalaxyGalaxyIndexes = listOf(
        GalaxyIndex(0, 4), GalaxyIndex(1, 9), GalaxyIndex(2, 0),
        GalaxyIndex(5, 8), GalaxyIndex(6, 1), GalaxyIndex(7, 12),
        GalaxyIndex(10, 9), GalaxyIndex(11, 0), GalaxyIndex(11, 5),
    )

    @Nested
    inner class ExpandGalaxy {
        @Test
        fun `it should expand galaxy`() {
            val expandedGalaxyResult = expandGalaxy(smallGalaxy)
            assertEquals(expandedGalaxy, expandedGalaxyResult)
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetGalaxyIndexes {
        private val smallGalaxyGalaxyIndexes = listOf(
            GalaxyIndex(0, 3), GalaxyIndex(1, 7), GalaxyIndex(2, 0),
            GalaxyIndex(4, 6), GalaxyIndex(5, 1), GalaxyIndex(6, 9),
            GalaxyIndex(8, 7), GalaxyIndex(9, 0), GalaxyIndex(9, 4),
        )

        @ParameterizedTest
        @MethodSource("galaxiesAndIndexes")
        fun `it should get galaxy indexes`(galaxy: List<String>, expectedGalaxyIndexes: List<GalaxyIndex>) {
            val galaxyIndexes = getGalaxyIndexes(galaxy)
            assertEquals(expectedGalaxyIndexes, galaxyIndexes)
        }

        private fun galaxiesAndIndexes() = listOf(
            Arguments.of(smallGalaxy, smallGalaxyGalaxyIndexes),
            Arguments.of(expandedGalaxy, expandedGalaxyGalaxyIndexes),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GalaxyIndexCountDistance {

        @ParameterizedTest
        @MethodSource("galaxiesAndDistances")
        fun `it should count distance between two galaxy indexes`(
            galaxyIndex: GalaxyIndex,
            otherGalaxyIndex: GalaxyIndex,
            expectedDistance: Int
        ) {
            val distance = galaxyIndex.distance(otherGalaxyIndex)
            assertEquals(expectedDistance, distance)
        }

        private fun galaxiesAndDistances() = listOf(
            Arguments.of(GalaxyIndex(6, 1), GalaxyIndex(11, 5), 9),
            Arguments.of(GalaxyIndex(0, 4), GalaxyIndex(10, 9), 15),
            Arguments.of(GalaxyIndex(2, 0), GalaxyIndex(7, 12), 17),
            Arguments.of(GalaxyIndex(11, 0), GalaxyIndex(11, 5), 5),
        )
    }

    @Nested
    inner class CountDistancesBetweenAllGalaxies {
        @Test
        fun `it should count distances between all galaxies`() {
            val distance = countDistancesBetweenAllGalaxies(expandedGalaxyGalaxyIndexes)
            val expectedDistance = 374
            assertEquals(expectedDistance, distance)
        }
    }

    @Nested
    inner class GetEmptyLinesIndexes {
        @Test
        fun `it should get empty lines indexes`() {
            val expectedEmptyLinesIndexes = listOf(3, 7)
            val emptyLinesIndexes = getEmptyLinesIndexes(smallGalaxy)
            assertEquals(expectedEmptyLinesIndexes, emptyLinesIndexes)
        }
    }

    @Nested
    inner class GetEmptyColumnsIndexes {
        @Test
        fun `it should get empty columns indexes`() {
            val expectedEmptyColumnsIndexes = listOf(2, 5, 8)
            val emptyColumnsIndexes = getEmptyColumnsIndexes(smallGalaxy)
            assertEquals(expectedEmptyColumnsIndexes, emptyColumnsIndexes)
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class CountDistancesBetweenAllGalaxiesWithEmptyMultiplier {
        @ParameterizedTest
        @MethodSource("galaxiesAndDistances")
        fun `it should count distance between two galaxy indexes`(
            galaxy: List<String>,
            multiplier: Int,
            expectedDistance: Long
        ) {
            val galaxyIndexes = getGalaxyIndexes(galaxy)
            val distance = countDistancesBetweenAllGalaxiesWithEmptyMultiplier(galaxy, galaxyIndexes, multiplier)
            assertEquals(expectedDistance, distance)
        }

        private fun galaxiesAndDistances() = listOf(
            Arguments.of(smallGalaxy, 2, 374L),
            Arguments.of(smallGalaxy, 10, 1030L),
            Arguments.of(smallGalaxy, 100, 8410L),
        )
    }
}