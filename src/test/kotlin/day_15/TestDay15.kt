package day_15

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class TestDay15 {

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class HashString {
        @ParameterizedTest
        @MethodSource("stringsAndHashes")
        fun `it should hash string`(stringToHash: String, expectedHashedValue: Int) {
            val hashedValue = hashString(stringToHash)
            assertEquals(expectedHashedValue, hashedValue)
        }

        private fun stringsAndHashes() = listOf(
            Arguments.of("H", 200),
            Arguments.of("HA", 153),
            Arguments.of("HAS", 172),
            Arguments.of("HASH", 52),
            Arguments.of("rn=1", 30),
            Arguments.of("cm-", 253),
            Arguments.of("qp=3", 97),
            Arguments.of("cm=2", 47),
            Arguments.of("qp-", 14),
            Arguments.of("pc=4", 180),
            Arguments.of("ot=9", 9),
            Arguments.of("ab=5", 197),
            Arguments.of("pc-", 48),
            Arguments.of("pc=6", 214),
            Arguments.of("ot=7", 231),
            Arguments.of("rn", 0),
            Arguments.of("qp", 1),
            Arguments.of("cm", 0),
            Arguments.of("pc", 3),
        )
    }

    @Nested
    inner class CountFocusingPower {
        @Test
        fun `it should count focusing power`() {
            val focusingPower = countFocusingPower("rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7".split(","))
            assertEquals(145, focusingPower)
        }
    }
}