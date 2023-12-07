package day_07

fun main() {
    val lines =
        object {}.javaClass.classLoader.getResourceAsStream("advent_of_code_2023_07.txt")?.bufferedReader()?.readLines()
            ?.stream()?.toList()!!

    val handAndBids = lines.map { parseRow(it) }
    val totalWinning = countTotalWinning(handAndBids, HandAndBid::compareTo)
    val totalWinningWithJokers = countTotalWinning(handAndBids, HandAndBid::compareWithJokers)
    println(totalWinning)
    println(totalWinningWithJokers)
}

fun parseRow(row: String): HandAndBid {
    val handAndBidStrings = row.split(" ")
    return HandAndBid(
        Hand(
            handAndBidStrings[0]
        ),
        handAndBidStrings[1].toInt()
    )
}

fun countTotalWinning(handAndBids: List<HandAndBid>, comparisonFunction: (a: HandAndBid, b: HandAndBid) -> Int): Int {
    var rank = 1
    var totalScore = 0
    handAndBids.stream().sorted(comparisonFunction).forEach {
        totalScore += it.bid * rank
        rank++
    }
    return totalScore
}