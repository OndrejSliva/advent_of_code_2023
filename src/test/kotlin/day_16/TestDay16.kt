package day_16

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class TestDay16 {

    private val map = listOf(
        ".|...\\....",
        "|.-.\\.....",
        ".....|-...",
        "........|.",
        "..........",
        ".........\\",
        "..../.\\\\..",
        ".-.-/..|..",
        ".|....-|.\\",
        "..//.|....",
    )

    @Nested
    inner class CountEnergizedFields {

        @Test
        fun `it should count energized fields`() {
            val energizedFields = countEnergizedFields(map, Beam(MapIndex(0, 0), Direction.RIGHT))
            assertEquals(46, energizedFields)
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class BeamMove {

        @ParameterizedTest
        @MethodSource("beamsAndBeamsAfterMove")
        fun `it should move in correct direction`(currentBeam: Beam, mapCharacter: Char, expectedBeams: List<Beam>) {
            val newBeams = currentBeam.move(mapCharacter)
            assertEquals(expectedBeams, newBeams)
        }

        private fun beamsAndBeamsAfterMove() = listOf(
            Arguments.of(Beam(MapIndex(1, 1), Direction.RIGHT), '.', listOf(Beam(MapIndex(1, 2), Direction.RIGHT))),
            Arguments.of(Beam(MapIndex(1, 1), Direction.RIGHT), '-', listOf(Beam(MapIndex(1, 2), Direction.RIGHT))),
            Arguments.of(Beam(MapIndex(1, 1), Direction.RIGHT), '/', listOf(Beam(MapIndex(0, 1), Direction.UP))),
            Arguments.of(Beam(MapIndex(1, 1), Direction.RIGHT), '\\', listOf(Beam(MapIndex(2, 1), Direction.DOWN))),
            Arguments.of(Beam(MapIndex(1, 1), Direction.RIGHT), '|', listOf(Beam(MapIndex(0, 1), Direction.UP), Beam(MapIndex(2, 1), Direction.DOWN))),

            Arguments.of(Beam(MapIndex(1, 1), Direction.LEFT), '.', listOf(Beam(MapIndex(1, 0), Direction.LEFT))),
            Arguments.of(Beam(MapIndex(1, 1), Direction.LEFT), '-', listOf(Beam(MapIndex(1, 0), Direction.LEFT))),
            Arguments.of(Beam(MapIndex(1, 1), Direction.LEFT), '/', listOf(Beam(MapIndex(2, 1), Direction.DOWN))),
            Arguments.of(Beam(MapIndex(1, 1), Direction.LEFT), '\\', listOf(Beam(MapIndex(0, 1), Direction.UP))),
            Arguments.of(Beam(MapIndex(1, 1), Direction.LEFT), '|', listOf(Beam(MapIndex(0, 1), Direction.UP), Beam(MapIndex(2, 1), Direction.DOWN))),

            Arguments.of(Beam(MapIndex(1, 1), Direction.UP), '.', listOf(Beam(MapIndex(0, 1), Direction.UP))),
            Arguments.of(Beam(MapIndex(1, 1), Direction.UP), '-', listOf(Beam(MapIndex(1, 0), Direction.LEFT), Beam(MapIndex(1, 2), Direction.RIGHT))),
            Arguments.of(Beam(MapIndex(1, 1), Direction.UP), '/', listOf(Beam(MapIndex(1, 2), Direction.RIGHT))),
            Arguments.of(Beam(MapIndex(1, 1), Direction.UP), '\\', listOf(Beam(MapIndex(1, 0), Direction.LEFT))),
            Arguments.of(Beam(MapIndex(1, 1), Direction.UP), '|', listOf(Beam(MapIndex(0, 1), Direction.UP))),

            Arguments.of(Beam(MapIndex(1, 1), Direction.DOWN), '.', listOf(Beam(MapIndex(2, 1), Direction.DOWN))),
            Arguments.of(Beam(MapIndex(1, 1), Direction.DOWN), '-', listOf(Beam(MapIndex(1, 0), Direction.LEFT), Beam(MapIndex(1, 2), Direction.RIGHT))),
            Arguments.of(Beam(MapIndex(1, 1), Direction.DOWN), '/', listOf(Beam(MapIndex(1, 0), Direction.LEFT))),
            Arguments.of(Beam(MapIndex(1, 1), Direction.DOWN), '\\', listOf(Beam(MapIndex(1, 2), Direction.RIGHT))),
            Arguments.of(Beam(MapIndex(1, 1), Direction.DOWN), '|', listOf(Beam(MapIndex(2, 1), Direction.DOWN))),
        )
    }

    @Nested
    inner class CountMostEnergizedFields {

        @Test
        fun `it should count energized fields`() {
            val energizedFields = countMostEnergizedFields(map)
            assertEquals(51, energizedFields)
        }
    }
}