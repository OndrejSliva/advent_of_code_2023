package day_08

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class TestDay08 {

    @Nested
    inner class ParseSteps {
        @Test
        fun `it should parse steps`() {
            val stepsString = "LLR"
            val expectedSteps = listOf(StepDirection.LEFT, StepDirection.LEFT, StepDirection.RIGHT)
            val parsedSteps = parseSteps(stepsString)
            assertEquals(expectedSteps, parsedSteps)
        }
    }

    @Nested
    inner class ParseNavigationRow {
        @Test
        fun `it should parse navigation row`() {
            val row = "AAA = (BBB, CCC)"
            val expectedNavigationRow = NavigationRow("AAA", "BBB", "CCC")
            val parsedRow = parseNavigationRow(row)
            assertEquals(expectedNavigationRow, parsedRow)
        }
    }

    @Nested
    inner class ParseMap {
        @Test
        fun `it should parse map`() {
            val lines = listOf(
                "AAA = (BBB, BBB)",
                "BBB = (AAA, ZZZ)",
                "ZZZ = (ZZZ, ZZZ)"
            )
            val expectedMap = NavigationMap(
                listOf(
                    NavigationRow("AAA", "BBB", "BBB"),
                    NavigationRow("BBB", "AAA", "ZZZ"),
                    NavigationRow("ZZZ", "ZZZ", "ZZZ"),
                )
            )
            val parsedMap = parseNavigationMap(lines)
            assertEquals(expectedMap, parsedMap)
        }
    }

    @Nested
    inner class NavigationMapCountSteps {
        @Test
        fun `it should count steps`() {
            val steps = listOf(StepDirection.LEFT, StepDirection.LEFT, StepDirection.RIGHT)
            val navigationMap = NavigationMap(
                listOf(
                    NavigationRow("AAA", "BBB", "BBB"),
                    NavigationRow("BBB", "AAA", "ZZZ"),
                    NavigationRow("ZZZ", "ZZZ", "ZZZ"),
                )
            )
            val stepCount = navigationMap.countSteps(steps, "AAA", "ZZZ")
            assertEquals(6, stepCount)
        }
    }

    @Nested
    inner class NavigationMapCountSteps2 {
        @Test
        fun `it should count steps`() {
            val steps = listOf(StepDirection.LEFT, StepDirection.RIGHT)
            val navigationMap = parseNavigationMap(
                listOf(
                    "11A = (11B, XXX)",
                    "11B = (XXX, 11Z)",
                    "11Z = (11B, XXX)",
                    "22A = (22B, XXX)",
                    "22B = (22C, 22C)",
                    "22C = (22Z, 22Z)",
                    "22Z = (22B, 22B)",
                    "XXX = (XXX, XXX)"
                )
            )
            val startingLocations = listOf("11A", "22A")
            val endLocations = listOf("11Z", "22Z")
            val stepCount = navigationMap.countSteps2(steps, startingLocations, endLocations)
            assertEquals(6, stepCount)
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class Gcd {

        @ParameterizedTest
        @MethodSource("gcdNumbers")
        fun `it should count gcd`(a: Long, b: Long, expectedResult: Long) {
            val result = gcd(a, b)
            assertEquals(expectedResult, result)
        }

        private fun gcdNumbers() = listOf(
            Arguments.of(2, 4, 2),
            Arguments.of(2, 8, 2),
            Arguments.of(3, 9, 3),
            Arguments.of(12, 6, 6),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class Lcm {

        @ParameterizedTest
        @MethodSource("lcmNumbers")
        fun `it should count lcm`(a: Long, b: Long, expectedResult: Long) {
            val result = lcm(a, b)
            assertEquals(expectedResult, result)
        }

        private fun lcmNumbers() = listOf(
            Arguments.of(2, 4, 4),
            Arguments.of(2, 8, 8),
            Arguments.of(3, 9, 9),
            Arguments.of(12, 6, 12),
            Arguments.of(12, 3, 12),
            Arguments.of(12, 20, 60),
        )
    }
}