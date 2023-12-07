package day_07

data class HandAndBid(
    val hand: Hand,
    val bid: Int
): Comparable<HandAndBid> {
    override fun compareTo(other: HandAndBid): Int {
        return hand.compareTo(other.hand)
    }
    fun compareWithJokers(other: HandAndBid): Int {
        return hand.compareWithJokers(other.hand)
    }
}
