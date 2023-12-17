package day_12

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class TestDay12 {

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class ParseRow {
        @ParameterizedTest
        @MethodSource("rowsAndParsedRows")
        fun `it should parse row`(row: String, expectedParsedRow: ArrangementRow) {
            val parsedRow = parseRow(row)
            assertEquals(expectedParsedRow, parsedRow)
        }

        private fun rowsAndParsedRows() = listOf(
            Arguments.of("???.### 1,1,3", ArrangementRow("???.###", listOf(1, 1, 3)))
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class CountPossibleArrangements {
        @ParameterizedTest
        @MethodSource("rowsAndPossibleArrangements")
        fun `it should parse row`(arrangementRow: ArrangementRow, expectedArrangements: Int) {
            val arrangements = arrangementRow.countPossibleArrangements()
            assertEquals(expectedArrangements, arrangements)
        }

        private fun rowsAndPossibleArrangements() = listOf(
            Arguments.of(ArrangementRow("???.###", listOf(1, 1, 3)), 1),
            Arguments.of(ArrangementRow(".??..??...?##.", listOf(1, 1, 3)), 4),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class CountPossibleArrangementsFolded {
        @ParameterizedTest
        @MethodSource("rowsAndPossibleArrangements")
        fun `it should parse row`(arrangementRow: ArrangementRow, fold: Int, expectedArrangements: Long) {
//            val arrangements = countPossibleArrangementsFolded2(arrangementRow, fold)
            val arrangements = countPossibleArrangementsFoldedWithCache(arrangementRow, fold, hashMapOf())
            assertEquals(expectedArrangements, arrangements)
        }

        private fun rowsAndPossibleArrangements() = listOf(
            Arguments.of(ArrangementRow("#.#.###", listOf(1, 1, 3)), 1, 1),
            Arguments.of(ArrangementRow("?.?.###", listOf(1, 1, 3)), 1, 1),
            Arguments.of(ArrangementRow("??.?.###", listOf(1, 1, 3)), 1, 2),
            Arguments.of(ArrangementRow("???.###", listOf(1, 1, 3)), 1, 1),
            Arguments.of(ArrangementRow("?#??.###", listOf(1, 1, 3)), 1, 1),
            Arguments.of(ArrangementRow(".??..??...?##.", listOf(1, 1, 3)), 1, 4),
            Arguments.of(ArrangementRow(".??..??...?##.", listOf(1, 1, 3)), 2, 32),
            Arguments.of(ArrangementRow(".??..??...?##.", listOf(1, 1, 3)), 5, 16384),
            Arguments.of(ArrangementRow("?#?#?#?#?#?#?#?", listOf(1,3,1,6)), 5, 1),
            Arguments.of(ArrangementRow("????.#...#...", listOf(4,1,1)), 5, 16),
            Arguments.of(ArrangementRow("????.######..#####.", listOf(1,6,5)), 2, 20),
            Arguments.of(ArrangementRow("?????", listOf(1)), 1, 5),
            Arguments.of(ArrangementRow("??.??", listOf(1)), 1, 4),
            Arguments.of(ArrangementRow("??.??", listOf(1, 1)), 1, 4),
            Arguments.of(ArrangementRow("????.######..#####.", listOf(1,6,5)), 5, 2500),
            Arguments.of(ArrangementRow("?###????????", listOf(3,2,1)), 5, 506250),
            Arguments.of(ArrangementRow("??.#??", listOf(2)), 1, 1),
            Arguments.of(ArrangementRow("??.???.#??", listOf(1,1,2)), 1, 7),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class CountBrokenSizeAtStart {
        @ParameterizedTest
        @MethodSource("rowsAndBrokenSizes")
        fun `it should count broken size`(row: String, expectedBrokenSize: Int) {
            val brokenSize = countBrokenSizeAtStart(row)
            assertEquals(expectedBrokenSize, brokenSize)
        }

        private fun rowsAndBrokenSizes() = listOf(
            Arguments.of("???.###", 3),
            Arguments.of("#???.###", 4),
            Arguments.of("#.###", 1),
            Arguments.of("?#.###", 2),
            Arguments.of(".??..??...?##.", 0),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class ConnectStrings {
        @ParameterizedTest
        @MethodSource("listSeparatorConnected")
        fun `it should connect string`(listsToConnect: List<String>, separator: String, expectedConnectedList: String) {
            val connectedList = connectList(listsToConnect, separator)
            assertEquals(expectedConnectedList, connectedList)
        }

        private fun listSeparatorConnected() = listOf(
            Arguments.of(listOf(".", "."), "?", ".?."),
            Arguments.of(listOf(".??..??...?##.", ".??..??...?##.", ".??..??...?##.", ".??..??...?##.", ".??..??...?##."),
                "?",
                ".??..??...?##.?.??..??...?##.?.??..??...?##.?.??..??...?##.?.??..??...?##."),
//            Arguments.of("#???.###", 4),
//            Arguments.of("#.###", 1),
//            Arguments.of("?#.###", 2),
//            Arguments.of(".??..??...?##.", 0),
        )
    }
}