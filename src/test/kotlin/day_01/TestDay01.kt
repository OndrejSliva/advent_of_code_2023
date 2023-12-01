package day_01

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class TestDay01 {

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCalibrationValue {
        @ParameterizedTest
        @MethodSource("calibrationValues")
        fun `it should get calibration value from string`(expectedCalibrationValue: Int, string: String) {
            val calibrationValue = getCalibrationValue(string)
            assertEquals(expectedCalibrationValue, calibrationValue)
        }

        private fun calibrationValues(): List<Arguments> = listOf(
            Arguments.of(12, "1abc2"),
            Arguments.of(38, "pqr3stu8vwx"),
            Arguments.of(15, "a1b2c3d4e5f"),
            Arguments.of(77, "treb7uchet"),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCalibrationValueV2 {
        @ParameterizedTest
        @MethodSource("calibrationValuesWithStringDigits")
        fun `it should get calibration value from string with string digits`(expectedCalibrationValue: Int, string: String) {
            val calibrationValue = getCalibrationValueV2(string)
            assertEquals(expectedCalibrationValue, calibrationValue)
        }

        private fun calibrationValuesWithStringDigits(): List<Arguments> = listOf(
            Arguments.of(29, "two1nine"),
            Arguments.of(83, "eightwothree"),
            Arguments.of(13, "abcone2threexyz"),
            Arguments.of(24, "xtwone3four"),
            Arguments.of(42, "4nineeightseven2"),
            Arguments.of(14, "zoneight234"),
            Arguments.of(76, "7pqrstsixteen"),

            Arguments.of(38, "threefjsvftzqneightfourtbvxqhssgrntdzpx2eighteight"),
            Arguments.of(31, "hsvrgtmkxcpxtjncmthreethreeone7one"),
            Arguments.of(87, "8gpvvvhpfqb6sevenhjldkhsjskthhmzzgqxsflseven"),
            Arguments.of(37, "3fivefivefoursevenflxvnbzlxhffgd"),
            Arguments.of(19, "sxgftzrr1pkffhkjtcv5mclnzsvdqktvkrgbnctgcnine"),
            Arguments.of(41, "trknlxnv43zxlrqjtwonect"),
        )
    }

}