package day_02

val colorLimit = TurnColors(red = 12, green = 13, blue = 14)

fun main() {
    var sum = 0
    var sum2 = 0
    object {}.javaClass.classLoader.getResourceAsStream("advent_of_code_2023_02.txt")?.bufferedReader()?.readLines()?.stream()?.forEach{
        val game = parseGame(it)
        if (isGamePossible(game)) {
            sum += game.gameId
        }
        val lowest = lowestPossible(game.turns)
        sum2 += lowest.powerOfCubes()
    }

    println(sum)
    println(sum2)
}

fun parseTurn(string: String): TurnColors {
    val colorsWithNumbers = string.split(',')
    var red = 0
    var green = 0
    var blue = 0
    colorsWithNumbers.forEach { colorWithNumber ->
        val numberAndColor = colorWithNumber.trim().split(' ')
        when (numberAndColor[1]) {
            "red" -> red = numberAndColor[0].toInt()
            "green" -> green = numberAndColor[0].toInt()
            "blue" -> blue = numberAndColor[0].toInt()
        }
    }
    return TurnColors(red, green, blue)
}

fun parseGame(string: String): Game {
    val gameAndTurns = string.split(':')
    val gameId = gameAndTurns[0].trim().split(' ')[1].toInt()
    val turns = gameAndTurns[1].split(';').map { turnString ->
        parseTurn(turnString.trim())
    }
    return Game(gameId, turns)
}

fun isGamePossible(game: Game) =
    game.turns.all { turn ->
        turn.red <= colorLimit.red &&
        turn.green <= colorLimit.green &&
        turn.blue <= colorLimit.blue
    }

fun lowestPossible(turns: List<TurnColors>): TurnColors {
    return TurnColors(
        turns.map { it.red }.max(),
        turns.map { it.green }.max(),
        turns.map { it.blue }.max(),
    )
}