package day_04

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class TestDay04 {

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class ParseCard {
        @ParameterizedTest
        @MethodSource("cards")
        fun `it should parse card values`(cardString: String, expectedCard: Card) {
            val parsedCard = parseCard(cardString)
            assertEquals(expectedCard, parsedCard)
        }

        private fun cards(): List<Arguments> = listOf(
            Arguments.of("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53", Card(1, setOf(41, 48, 83, 86, 17), setOf(83, 86, 6, 31, 17, 9, 48, 53))),
            Arguments.of("Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19", Card(2, setOf(13, 32, 20, 16, 61), setOf(61, 30, 68, 82, 17, 32, 24, 19))),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class CalculateScore {
        @ParameterizedTest
        @MethodSource("cards")
        fun `it should parse card values`(card: Card, expectedScore: Int) {
            assertEquals(expectedScore, card.score())
        }

        private fun cards(): List<Arguments> = listOf(
            Arguments.of(Card(1, setOf(41, 48, 83, 86, 17), setOf(83, 86, 6, 31, 17, 9, 48, 53)), 8),
            Arguments.of(Card(2, setOf(13, 32, 20, 16, 61), setOf(61, 30, 68, 82, 17, 32, 24, 19)), 2),
            Arguments.of(Card(3, setOf(41, 92, 73, 84, 69), setOf(59, 84, 76, 51, 58, 5, 54, 83)), 1),
            Arguments.of(Card(4, setOf(87, 83, 26, 28, 32), setOf(88, 30, 70, 12, 93, 22, 82, 36)), 0),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class CountCards {
        @ParameterizedTest
        @MethodSource("cards")
        fun `it should parse card values`(cards: List<Card>, expectedCards: Int) {
            assertEquals(expectedCards, countCards(cards))
        }

        private fun cards(): List<Arguments> = listOf(
            Arguments.of(
                listOf(Card(1, setOf(41, 48, 83, 86, 17), setOf(83, 86, 6, 31, 17, 9, 48, 53))),
                1
            ),
            Arguments.of(
                listOf(
                    Card(1, setOf(41, 48, 83, 86, 17), setOf(83, 86, 6, 31, 17, 9, 48, 53)),
                    Card(2, setOf(13, 32, 20, 16, 61), setOf(61, 30, 68, 82, 17, 32, 24, 19)),
                ),
                3
            ),
            Arguments.of(
                listOf(
                    Card(1, setOf(41, 48, 83, 86, 17), setOf(83, 86, 6, 31, 17, 9, 48, 53)),
                    Card(2, setOf(13, 32, 20, 16, 61), setOf(61, 30, 68, 82, 17, 32, 24, 19)),
                    Card(3, setOf(1, 21, 53, 59, 44), setOf(69, 82, 63, 72, 16, 21, 14, 1)),
                ),
                7
            ),
            Arguments.of(
                listOf(
                    Card(1, setOf(41, 48, 83, 86, 17), setOf(83, 86, 6, 31, 17, 9, 48, 53)),
                    Card(2, setOf(13, 32, 20, 16, 61), setOf(61, 30, 68, 82, 17, 32, 24, 19)),
                    Card(3, setOf(1, 21, 53, 59, 44), setOf(69, 82, 63, 72, 16, 21, 14, 1)),
                    Card(4, setOf(41, 92, 73, 84, 69), setOf(59, 84, 76, 51, 58, 5, 54, 83)),
                    Card(5, setOf(87, 83, 26, 28, 32), setOf(88, 30, 70, 12, 93, 22, 82, 36)),
                    Card(6, setOf(31, 18, 13, 56, 72), setOf(74, 77, 10, 23, 35, 67, 36, 11)),
                ),
                30
            ),
        )
    }
}