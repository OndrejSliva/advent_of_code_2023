package day_07

data class Hand(
    val hand: String,
): Comparable<Hand> {
    val type: HandType
    val typeWithJokers: HandType

    init {
        type = countType()
        typeWithJokers = countTypeWithJokers()
    }

    private fun countType(): HandType {
        val occurrences = hashMapOf<Char, Int>()
        for (c in hand) {
            occurrences.putIfAbsent(c, 0)
            occurrences[c] = occurrences[c]!! + 1
        }
        val sorted = occurrences.values.sorted().reversed()
        val handType = when {
            sorted[0] == 5 -> HandType.FIVE_OF_A_KIND
            sorted[0] == 4 -> HandType.FOUR_OF_A_KIND
            sorted[0] == 3 && sorted[1] == 2 -> HandType.FULL_HOUSE
            sorted[0] == 3 -> HandType.THREE_OF_A_KIND
            sorted[0] == 2 && sorted[1] == 2 -> HandType.TWO_PAIR
            sorted[0] == 2 -> HandType.ONE_PAIR
            else -> HandType.HIGH_CARD
        }

        return handType
    }

    private fun countTypeWithJokers(): HandType {
        val occurrences = hashMapOf<Char, Int>()
        for (c in hand) {
            occurrences.putIfAbsent(c, 0)
            occurrences[c] = occurrences[c]!! + 1
        }
        val countOfJokers = occurrences.getOrDefault('J', 0)
        val maxValue = occurrences.maxBy { it.value }
        val sorted = occurrences.values.sorted().reversed()
        val maxCount = if(countOfJokers == 5) {
            5
        } else if (maxValue.key == 'J') {
            sorted[1] + countOfJokers
        } else {
            sorted[0] + countOfJokers
        }
        val handType = when {
            maxCount == 5 -> HandType.FIVE_OF_A_KIND
            maxCount == 4 -> HandType.FOUR_OF_A_KIND
            maxCount == 3 && sorted[1] == 2 -> HandType.FULL_HOUSE
            maxCount == 3 -> HandType.THREE_OF_A_KIND
            maxCount == 2 && sorted[1] == 2 -> HandType.TWO_PAIR
            maxCount == 2 -> HandType.ONE_PAIR
            else -> HandType.HIGH_CARD
        }

        return handType
    }

    override fun compareTo(other: Hand): Int {
        if (type == other.type) {
            for (idx in hand.indices) {
                val comparison = charOrder.indexOf(other.hand[idx]) - charOrder.indexOf(hand[idx])
                if (comparison != 0) {
                    return comparison
                }
            }
            return 0
        }
        return typeOrder.indexOf(other.type) - typeOrder.indexOf(type)
    }

    fun compareWithJokers(other: Hand): Int {
        if (typeWithJokers == other.typeWithJokers) {
            for (idx in hand.indices) {
                val comparison = charOrderJokers.indexOf(other.hand[idx]) - charOrderJokers.indexOf(hand[idx])
                if (comparison != 0) {
                    return comparison
                }
            }
            return 0
        }
        return typeOrder.indexOf(other.typeWithJokers) - typeOrder.indexOf(typeWithJokers)
    }

    companion object {
        private val typeOrder = listOf(
            HandType.FIVE_OF_A_KIND,
            HandType.FOUR_OF_A_KIND,
            HandType.FULL_HOUSE,
            HandType.THREE_OF_A_KIND,
            HandType.TWO_PAIR,
            HandType.ONE_PAIR,
            HandType.HIGH_CARD
        )
    }

    private val charOrder = listOf(
        'A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2'
    )

    private val charOrderJokers = listOf(
        'A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J'
    )
}
