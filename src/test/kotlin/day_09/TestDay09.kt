package day_09

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class TestDay09 {

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class ParseRow {
        @ParameterizedTest
        @MethodSource("rows")
        fun `it should parse row`(row: String, expectedList: List<Int>) {
            val parsedRow = parseRow(row)
            assertEquals(expectedList, parsedRow)
        }

        private fun rows() = listOf(
            Arguments.of("0   3   6   9  12  15", listOf(0, 3, 6, 9, 12, 15)),
            Arguments.of("0 -4 -8 -12 -16", listOf(0, -4, -8, -12, -16))
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class FindDifferences {
        @ParameterizedTest
        @MethodSource("sequences")
        fun `it should find differences between list`(sequence: List<Int>, expectedDifferences: List<Int>) {
            val differences = getDifferences(sequence)
            assertEquals(expectedDifferences, differences)
        }

        private fun sequences() = listOf(
            Arguments.of(listOf(0, 3, 6, 9, 12, 15), listOf(3, 3, 3, 3, 3)),
            Arguments.of(listOf(3, 3, 3, 3, 3), listOf(0, 0, 0, 0)),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class FindNext {
        @ParameterizedTest
        @MethodSource("sequences")
        fun `it should find next value`(sequence: List<Int>, expectedNext: Int) {
            val next = findNext(sequence)
            assertEquals(expectedNext, next)
        }

        private fun sequences() = listOf(
            Arguments.of(listOf(0, 3, 6, 9, 12, 15), 18),
            Arguments.of(listOf(3, 3, 3, 3, 3), 3),
            Arguments.of(listOf(1, 3, 6, 10, 15, 21), 28),
            Arguments.of(listOf(0, 0, 0, 0), 0),
            Arguments.of(listOf(2, 3, 4, 5, 6), 7),
            Arguments.of(listOf(10, 13, 16, 21, 30, 45), 68),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class FindPrevious {
        @ParameterizedTest
        @MethodSource("sequences")
        fun `it should find previous value`(sequence: List<Int>, expectedPrevious: Int) {
            val previous = findPrevious(sequence)
            assertEquals(expectedPrevious, previous)
        }

        private fun sequences() = listOf(
            Arguments.of(listOf(10, 13, 16, 21, 30, 45), 5),
            Arguments.of(listOf(3, 3, 5, 9, 15), 5),
            Arguments.of(listOf(0, 2, 4, 6), -2),
            Arguments.of(listOf(2, 2, 2), 2),
            Arguments.of(listOf(0, 0), 0),
        )
    }
}