package day_07

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.math.min
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestDay07 {

    @Nested
    inner class ParseRow {
        @Test
        fun `it should parse row`() {
            val row = "32T3K 765"
            val handAndBid = parseRow(row)
            val expectedHandAndBid = HandAndBid(Hand("32T3K"), 765)
            assertEquals(expectedHandAndBid, handAndBid)
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class HandAndBidType {

        @ParameterizedTest
        @MethodSource("handsAndTypes")
        fun `it should get correct type`(handString: String, expectedType: HandType) {
            val hand = Hand(handString)

            assertEquals(expectedType, hand.type)
        }

        private fun handsAndTypes() = listOf(
            Arguments.of("AAAAA", HandType.FIVE_OF_A_KIND),
            Arguments.of("AAAAK", HandType.FOUR_OF_A_KIND),
            Arguments.of("AAAKK", HandType.FULL_HOUSE),
            Arguments.of("AAAKQ", HandType.THREE_OF_A_KIND),
            Arguments.of("AAKKQ", HandType.TWO_PAIR),
            Arguments.of("AAKQJ", HandType.ONE_PAIR),
            Arguments.of("AKQJT", HandType.HIGH_CARD),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class HandCompare {
        @Test
        fun `same hands should be same`() {
            assertEquals(0, Hand("AAAAA").compareTo(Hand("AAAAA")))
        }

        @ParameterizedTest
        @MethodSource("biggerAndSmallerHands")
        fun `it should compare hands`(biggerHandString: String, smallerHandString: String) {
            assertTrue(Hand(biggerHandString) > Hand(smallerHandString))
        }

        private fun biggerAndSmallerHands() = listOf(
            Arguments.of("AAAAA", "AAAAK"),
            Arguments.of("AAAAK", "AAAKK"),
            Arguments.of("AAAAA", "AAAKK"),
            Arguments.of("KK677", "KTJJT"),
            Arguments.of("QQQJA", "T55J5"),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class HandAndBidCompare {
        @ParameterizedTest
        @MethodSource("biggerAndSmallerHands")
        fun `it compare hand and bids by hands`(biggerHandString: String, smallerHandString: String) {
            assertTrue(HandAndBid(Hand(biggerHandString), 1) > HandAndBid(Hand(smallerHandString), 2))
        }

        private fun biggerAndSmallerHands() = listOf(
            Arguments.of("AAAAA", "AAAAK"),
            Arguments.of("AAAAK", "AAAKK"),
            Arguments.of("AAAAA", "AAAKK"),
            Arguments.of("KK677", "KTJJT"),
            Arguments.of("QQQJA", "T55J5"),
        )
    }

    @Nested
    inner class CountTotalWinning {
        private val handAndBids = listOf(
            HandAndBid(Hand("32T3K"), 765),
            HandAndBid(Hand("T55J5"), 684),
            HandAndBid(Hand("KK677"), 28),
            HandAndBid(Hand("KTJJT"), 220),
            HandAndBid(Hand("QQQJA"), 483),
        )

        @Test
        fun `it should count total winnig`() {
            val expectedTotalWinning = 6440
            val totalWinning = countTotalWinning(handAndBids, HandAndBid::compareTo)
            assertEquals(expectedTotalWinning, totalWinning)
        }

        @Test
        fun `it should count total winning with jokers`() {
            val expectedTotalWinning = 5905
            val totalWinning = countTotalWinning(handAndBids, HandAndBid::compareWithJokers)
            assertEquals(expectedTotalWinning, totalWinning)
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class HandAndBidTypeWithJokers {

        @ParameterizedTest
        @MethodSource("handsAndTypes")
        fun `it should get correct type`(handString: String, expectedType: HandType) {
            val hand = Hand(handString)

            assertEquals(expectedType, hand.typeWithJokers)
        }

        private fun handsAndTypes() = listOf(
            Arguments.of("AAAAA", HandType.FIVE_OF_A_KIND),
            Arguments.of("AAAAK", HandType.FOUR_OF_A_KIND),
            Arguments.of("AAAKK", HandType.FULL_HOUSE),
            Arguments.of("AAAKQ", HandType.THREE_OF_A_KIND),
            Arguments.of("AAKKQ", HandType.TWO_PAIR),
            Arguments.of("AAKQT", HandType.ONE_PAIR),
            Arguments.of("AKQT9", HandType.HIGH_CARD),
            Arguments.of("AAKQJ", HandType.THREE_OF_A_KIND),
            Arguments.of("AKQJT", HandType.ONE_PAIR),
            Arguments.of("32T3K", HandType.ONE_PAIR),
            Arguments.of("KK677", HandType.TWO_PAIR),
            Arguments.of("T55J5", HandType.FOUR_OF_A_KIND),
            Arguments.of("KTJJT", HandType.FOUR_OF_A_KIND),
            Arguments.of("QQQJA", HandType.FOUR_OF_A_KIND),
            Arguments.of("26KQJ", HandType.ONE_PAIR),
            Arguments.of("JJJAA", HandType.FIVE_OF_A_KIND),
            Arguments.of("JJJJJ", HandType.FIVE_OF_A_KIND),
            Arguments.of("KK677", HandType.TWO_PAIR),
            Arguments.of("KTJJT", HandType.FOUR_OF_A_KIND),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class HandCompareWithJokers {
        @Test
        fun `same hands should be same`() {
            assertEquals(0, Hand("AAAAA").compareTo(Hand("AAAAA")))
        }

        @ParameterizedTest
        @MethodSource("biggerAndSmallerHands")
        fun `it should compare hands`(biggerHandString: String, smallerHandString: String) {
            val result = Hand(biggerHandString).compareWithJokers(Hand(smallerHandString))
            println("$biggerHandString [${Hand(biggerHandString).typeWithJokers}] > $smallerHandString [${Hand(smallerHandString).typeWithJokers}]: $result")
            val normalized = min(result, 1)
            assertEquals(1, normalized)
        }

        private fun biggerAndSmallerHands() = listOf(
            Arguments.of("AAAAA", "AAAAK"),
            Arguments.of("AAAAK", "AAAKK"),
            Arguments.of("AAAAA", "AAAKK"),
            Arguments.of("KTJJT", "KK677"),
            Arguments.of("QQQJA", "T55J5"),
        )
    }
}