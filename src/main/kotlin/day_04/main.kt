package day_04

fun main() {
    val cards = arrayListOf<Card>()

    var totalScore = 0
    object {}.javaClass.classLoader.getResourceAsStream("advent_of_code_2023_04.txt")?.bufferedReader()?.readLines()?.stream()?.forEach{
        val card = parseCard(it)
        cards.add(card)
        val score = card.score()

        totalScore += score
    }

    println(totalScore)
    val totalCards = countCards(cards)
    println(totalCards)
}

fun parseCard(cardString: String): Card {
    val idAndGame = cardString.split(':')
    val id = idAndGame[0].split(' ').filter { it.isNotEmpty() }[1].toInt()
    val winningAndMyNumbers = idAndGame[1].trim().split('|')
    val winning = toSetOfInts(winningAndMyNumbers[0])
    val my = toSetOfInts(winningAndMyNumbers[1])
    return Card(id, winning, my)
}

private fun toSetOfInts(string: String) = string.trim()
    .split(' ')
    .filter { it.isNotEmpty() }
    .map { it.toInt() }
    .toSet()

fun countCards(cards: List<Card>): Int {
    val allCopies = hashMapOf<Int, Int>()
    var total = 0
    cards.forEach { card ->
        val currentCopies = allCopies.getOrDefault(card.id, 1)
        val same = card.same()
        for (i in 1..same) {
            val cardIdx = card.id + i
            val copiesOfCardOnIdx = allCopies.getOrDefault(cardIdx, 1)
            allCopies[cardIdx] = copiesOfCardOnIdx + currentCopies
        }
        total += currentCopies
    }
    return total
}