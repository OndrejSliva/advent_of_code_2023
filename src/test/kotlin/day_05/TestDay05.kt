package day_05

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class TestDay05 {

    @Nested
    inner class ParseTranslationRow {
        @Test
        fun `it should parse row to TranslationRow`() {
            val row = "50 98 2"
            val expectedRow = TranslationRow(50, 98, 2)
            assertEquals(expectedRow, parseTranslationRow(row))
        }
    }

    @Nested
    inner class ParseTranslationMap {
        @Test
        fun `it should parse rows to TranslationMap`() {
            val rows = listOf(
                "seed-to-soil map:",
                "50 98 2",
                "52 50 48"
            )
            val expectedMap = TranslationMap(
                from = "seed",
                to = "soil",
                translations = listOf(
                    TranslationRow(50, 98, 2),
                    TranslationRow(52, 50, 48),
                )
            )

            assertEquals(expectedMap, parseTranslationMap(rows))
        }
    }

    @Nested
    inner class ParseTranslationAtlas {
        @Test
        fun `it should parse rows to TranslationAtlas`() {
            val rows = listOf(
                "seed-to-soil map:",
                "50 98 2",
                "52 50 48",
                "",
                "soil-to-fertilizer map:",
                "0 15 37",
                "37 52 2",
                "39 0 15",
            )
            val firstMap = TranslationMap(
                from = "seed",
                to = "soil",
                translations = listOf(
                    TranslationRow(50, 98, 2),
                    TranslationRow(52, 50, 48),
                )
            )
            val secondMap = TranslationMap(
                from = "soil",
                to = "fertilizer",
                translations = listOf(
                    TranslationRow(0, 15, 37),
                    TranslationRow(37, 52, 2),
                    TranslationRow(39, 0, 15),
                )
            )
            val expectedAtlas = TranslationAtlas(
                listOf(firstMap, secondMap)
            )

            assertEquals(expectedAtlas, parseTranslationAtlas(rows))
        }
    }

    @Nested
    inner class ParseSeeds {
        @Test
        fun `it should parse row to TranslationRow`() {
            val row = "seeds: 79 14 55 13"

            val expectedSeeds = listOf<Long>(79, 14, 55, 13)

            assertEquals(expectedSeeds, parseSeeds(row))
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class RowTranslate {

        private val translationRow = TranslationRow(52, 50, 48)

        @ParameterizedTest
        @MethodSource("translations")
        fun `it should translate value in row`(source: Long, expectedTranslation: Long) {
            assertEquals(expectedTranslation, translationRow.translate(source))
        }

        private fun translations() = listOf(
            Arguments.of(79, 81),
            Arguments.of(14, 14),
            Arguments.of(55, 57),
            Arguments.of(13, 13),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class MapTranslate {
        private val translationMap = TranslationMap(
            from = "seed",
            to = "soil",
            translations = listOf(
                TranslationRow(50, 98, 2),
                TranslationRow(52, 50, 48),
            )
        )

        @ParameterizedTest
        @MethodSource("translations")
        fun `it should translate value in map`(source: Long, expectedTranslation: Long) {
            assertEquals(expectedTranslation, translationMap.translate(source))
        }

        private fun translations() = listOf(
            Arguments.of(79, 81),
            Arguments.of(14, 14),
            Arguments.of(55, 57),
            Arguments.of(13, 13),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class AtlasTranslate {
        private val exampleInput = listOf(
            "seed-to-soil map:",
            "50 98 2",
            "52 50 48",
            "",
            "soil-to-fertilizer map:",
            "0 15 37",
            "37 52 2",
            "39 0 15",
            "",
            "fertilizer-to-water map:",
            "49 53 8",
            "0 11 42",
            "42 0 7",
            "57 7 4",
            "",
            "water-to-light map:",
            "88 18 7",
            "18 25 70",
            "",
            "light-to-temperature map:",
            "45 77 23",
            "81 45 19",
            "68 64 13",
            "",
            "temperature-to-humidity map:",
            "0 69 1",
            "1 0 69",
            "",
            "humidity-to-location map:",
            "60 56 37",
            "56 93 4"
        )
        private val translationAtlas =
            parseTranslationAtlas(exampleInput)  // ugly, should be created object but saves ton of time

        @ParameterizedTest
        @MethodSource("translations")
        fun `it should translate value in atlas`(from: String, to: String, source: Long, expectedTranslation: Long) {
            assertEquals(expectedTranslation, translationAtlas.translate(from, to, source))
        }

        private fun translations() = listOf(
            Arguments.of("seed", "soil", 79, 81),
            Arguments.of("seed", "fertilizer", 79, 81),
            Arguments.of("seed", "water", 79, 81),
            Arguments.of("seed", "light", 79, 74),
            Arguments.of("seed", "temperature", 79, 78),
            Arguments.of("seed", "humidity", 79, 78),
            Arguments.of("seed", "location", 79, 82),
        )
    }

    @Nested
    inner class ParseSeedRanges {
        @Test
        fun `it should parse seed ranges`() {
            val row = "seeds: 79 14 55 13"
            val expectedSeeds = listOf<Long>(
                79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92,
                55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67
            )
            assertEquals(expectedSeeds, parseSeedRanges(row))
        }
    }
}