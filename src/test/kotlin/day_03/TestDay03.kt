package day_03

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestDay03 {

    @Nested
    inner class GetAdjustedNumbers {
        @Test
        fun `it should get adjusted numbers`() {
            val input = listOf(
                "467..114..",
                "...*......",
                "617.....+.",
                "....#...21",
                "..5.......",
            )
            val adjustedNumbers = getAdjustedNumbers(input)
            val expectedOutput = listOf(467, 617, 21)
            assertEquals(expectedOutput, adjustedNumbers)
        }

    }


    @Nested
    inner class GetNumbersPositions {
        @Test
        fun `it should get number positions from lines`() {
            val lines = listOf(
                "467..114..",
                "...*......",
                "617.....+.",
                "....#...21",
                "..5.......",
            )
            val numberPositions = getNumbersPositions(lines)
            val expectedPositions = listOf(
                NumberPosition(0, 0, 3, 467),
                NumberPosition(0, 5, 3, 114),
                NumberPosition(2, 0, 3, 617),
                NumberPosition(3, 8, 2, 21),
                NumberPosition(4, 2, 1, 5),
            )
            assertEquals(expectedPositions, numberPositions)
        }
    }

    @Nested
    inner class GetGearPositions {
        @Test
        fun `it should find gear positions`() {
            val lines = listOf(
                "467..114..",
                "...*......",
                "617.....+.",
                "....#...21",
                "..5.......",
            )
            val numberPositions = getGearPositions(lines)
            val expectedPositions = listOf(
                Position(1, 3)
            )
            assertEquals(expectedPositions, numberPositions)
        }
    }

    @Nested
    inner class GetGearRatios {
        @Test
        fun `it should find gear ratios`() {
            val lines = listOf(
                "467..114..",
                "...*......",
                "617.....+.",
                "....#...21",
                "..5.......",
            )
            val numberPositions = getGearRatios(lines)
            val expectedPositions = listOf(288139)
            assertEquals(expectedPositions, numberPositions)
        }
    }
}