package day_13

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class TestDay13 {

    private val inputString = listOf(
            "#.##..##.",
            "..#.##.#.",
            "##......#",
            "##......#",
            "..#.##.#.",
            "..##..##.",
            "#.#.##.#.",
            "",
            "#...##..#",
            "#....#..#",
            "..##..###",
            "#####.##.",
            "#####.##.",
            "..##..###",
            "#....#..#"
    )

    private val pattern1 = Pattern(
        listOf(
            "#.##..##.",
            "..#.##.#.",
            "##......#",
            "##......#",
            "..#.##.#.",
            "..##..##.",
            "#.#.##.#.",
        )
    )

    private val pattern2 = Pattern(
        listOf(
            "#...##..#",
            "#....#..#",
            "..##..###",
            "#####.##.",
            "#####.##.",
            "..##..###",
            "#....#..#",
        )
    )

    @Nested
    inner class ParsePatterns {
        @Test
        fun `it should parse patterns`() {
            val expectedPatterns = listOf(pattern1, pattern2)
            val parsedPattern = parsePatterns(inputString)
            assertEquals(expectedPatterns, parsedPattern)
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatternReflectionNumber {

        @ParameterizedTest
        @MethodSource("patternsAndReflectionNumbers")
        fun `it should find reflection number`(pattern: Pattern, expectedReflectionNumber: Int) {
            val reflectionNumber = pattern.findReflectionNumber()
            assertEquals(expectedReflectionNumber, reflectionNumber)
        }

        private fun patternsAndReflectionNumbers() = listOf(
            Arguments.of(pattern1, 5),
            Arguments.of(pattern2, 400),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class IsRowSymmetricalByIdx {

        @ParameterizedTest
        @MethodSource("rowsAndIndexes")
        fun `it should find reflection number`(row: String, index: Int, expectedResult: Boolean) {
            val result = isRowSymmetricalByIdx(row, index)
            assertEquals(expectedResult, result)
        }

        private fun rowsAndIndexes() = listOf(
            Arguments.of("#.##..##.", 5, true),
            Arguments.of("..#.##.#.", 5, true),
            Arguments.of("..#.##.#.", 4, false),
            Arguments.of("##.##.#", 4, true),
            Arguments.of("##.#..#.####...##", 16, true),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class FindSmudgeReflection {

        @ParameterizedTest
        @MethodSource("rowsAndIndexes")
        fun `it should find reflection number`(pattern: Pattern, expectedReflection: ReflectionIndexes) {
            val reflection = pattern.findSmudgeReflection()
            assertEquals(expectedReflection, reflection)
        }

        private fun rowsAndIndexes() = listOf(
            Arguments.of(pattern1, ReflectionIndexes(listOf(), listOf(3))),
            Arguments.of(pattern2, ReflectionIndexes(listOf(), listOf(1))),
        )
    }
}