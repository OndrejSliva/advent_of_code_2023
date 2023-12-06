package day_06

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestDay06 {

    @Nested
    inner class ParseRaces {
        @Test
        fun `it should parse races`() {
            val input = listOf(
                "Time:        57     72     69     92",
                "Distance:   291   1172   1176   2026"
            )

            val expectedRaces = listOf(
                Race(57, 291),
                Race(72, 1172),
                Race(69, 1176),
                Race(92, 2026),
            )

            val parsedRaces = parseRaces(input)
            assertEquals(expectedRaces, parsedRaces)
        }
    }

    @Nested
    inner class GetPossibleDistances {
        @Test
        fun `it should get possible distances`() {
            val race = Race(7, 9)
            val expectedDistances = listOf<Long>(0, 6, 10, 12, 12, 10, 6, 0)
            val possibleDistances = race.getPossibleDistances()
            assertEquals(expectedDistances, possibleDistances)
        }
    }

    @Nested
    inner class GetBeatingPossibilitiesCount {
        @Test
        fun `it should get possible distances`() {
            val race = Race(7, 9)
            val beatingPossibilities = race.getBeatingPossibilitiesCount()
            assertEquals(4, beatingPossibilities)
        }
    }

    @Nested
    inner class ParseRace {
        @Test
        fun `it should parse race`() {
            val input = listOf(
                "Time:        57     72     69     92",
                "Distance:   291   1172   1176   2026"
            )

            val expectedRace = Race(57726992, 291117211762026)

            val parsedRaces = parseRace(input)
            assertEquals(expectedRace, parsedRaces)
        }
    }
}