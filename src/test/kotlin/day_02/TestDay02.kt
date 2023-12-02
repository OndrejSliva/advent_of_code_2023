package day_02

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class TestDay02 {

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class ParseTurn {
        @ParameterizedTest
        @MethodSource("gameTurnStrings")
        fun `it should parse game turn`(turnString: String, expectedTurnColors: TurnColors) {
            assertEquals(expectedTurnColors, parseTurn(turnString))
        }

        private fun gameTurnStrings() = listOf(
            Arguments.of("3 blue, 4 red", TurnColors(red = 4, green = 0, blue = 3)),
            Arguments.of("1 red, 2 green, 6 blue", TurnColors(red = 1, green = 2, blue = 6)),
            Arguments.of("2 green", TurnColors(red = 0, green = 2, blue = 0)),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class ParseGame {
        @ParameterizedTest
        @MethodSource("games")
        fun `it should parse game turns`(gameString: String, expectedGame: Game) {
            assertEquals(expectedGame, parseGame(gameString))
        }

        private fun games() = listOf(
            Arguments.of("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
                Game(1, listOf(
                    TurnColors(red = 4, green = 0, blue = 3),
                    TurnColors(red = 1, green = 2, blue = 6),
                    TurnColors(red = 0, green = 2, blue = 0))
                )
            ),
            Arguments.of("Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
                Game(4, listOf(
                    TurnColors(red = 3, green = 1, blue = 6),
                    TurnColors(red = 6, green = 3, blue = 0),
                    TurnColors(red = 14, green = 3, blue = 15))
                )
            ),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class IsGamePossible {
        @ParameterizedTest
        @MethodSource("games")
        fun `it should decide if game is possible`(game: Game, isPossible: Boolean) {
            assertEquals(isPossible, isGamePossible(game))
        }

        private fun games() = listOf(
            Arguments.of(
                Game(1, listOf(
                    TurnColors(red = 4, green = 0, blue = 3),
                    TurnColors(red = 1, green = 2, blue = 6),
                    TurnColors(red = 0, green = 2, blue = 0))
                ),
                true
            ),
            Arguments.of(
                Game(4, listOf(
                    TurnColors(red = 3, green = 1, blue = 6),
                    TurnColors(red = 6, green = 3, blue = 0),
                    TurnColors(red = 14, green = 3, blue = 15))
                ),
                false
            ),
            Arguments.of(
                Game(4, listOf(
                    TurnColors(red = 20, green = 8, blue = 6),
                    TurnColors(red = 4, green = 13, blue = 5),
                    TurnColors(red = 1, green = 5, blue = 0))
                ),
                false
            ),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PowerOfCubes {
        @ParameterizedTest
        @MethodSource("turnsAndPowers")
        fun `it should decide if game is possible`(turn: TurnColors, power: Int) {
            assertEquals(power, turn.powerOfCubes())
        }

        private fun turnsAndPowers() = listOf(
            Arguments.of(TurnColors(red = 4, green = 0, blue = 3), 0),
            Arguments.of(TurnColors(red = 1, green = 2, blue = 6), 12),
            Arguments.of(TurnColors(red = 0, green = 2, blue = 0), 0),
            Arguments.of(TurnColors(red = 3, green = 1, blue = 6), 18),
            Arguments.of(TurnColors(red = 6, green = 3, blue = 0), 0),
            Arguments.of(TurnColors(red = 14, green = 3, blue = 15), 630),
            Arguments.of(TurnColors(red = 20, green = 8, blue = 6), 960),
            Arguments.of(TurnColors(red = 4, green = 13, blue = 5), 260),
            Arguments.of(TurnColors(red = 1, green = 5, blue = 0), 0),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class LowestPossible {
        @ParameterizedTest
        @MethodSource("lowestPossibleTurns")
        fun `it should decide if game is possible`(turns: List<TurnColors>, lowest: TurnColors) {
            assertEquals(lowest, lowestPossible(turns))
        }

        private fun lowestPossibleTurns() = listOf(
            Arguments.of(
                listOf(
                    TurnColors(red = 4, green = 0, blue = 3),
                    TurnColors(red = 1, green = 2, blue = 6),
                    TurnColors(red = 0, green = 2, blue = 0)
                ),
                TurnColors(red = 4, green = 2, blue = 6)
            ),
            Arguments.of(
                listOf(
                    TurnColors(red = 3, green = 1, blue = 6),
                    TurnColors(red = 6, green = 3, blue = 0),
                    TurnColors(red = 14, green = 3, blue = 15)
                ),
                TurnColors(red = 14, green = 3, blue = 15)
            ),
            Arguments.of(
                listOf(
                    TurnColors(red = 20, green = 8, blue = 6),
                    TurnColors(red = 4, green = 13, blue = 5),
                    TurnColors(red = 1, green = 5, blue = 0)
                ),
                TurnColors(red = 20, green = 13, blue = 6)
            ),
        )
    }
}