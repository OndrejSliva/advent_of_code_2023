package day_06

fun main() {
    val lines =
        object {}.javaClass.classLoader.getResourceAsStream("advent_of_code_2023_06.txt")?.bufferedReader()?.readLines()
            ?.stream()?.toList()!!

    val races = parseRaces(lines)
    val result1 = races.map { it.getBeatingPossibilitiesCount() }.reduce { sum, element -> sum * element }
    val race = parseRace(lines)
    val result2 = race.getBeatingPossibilitiesCount()
    println(result1)
    println(result2)
}

fun parseRaces(input: List<String>): List<Race> {
    val times = toListOfLongs(input[0])
    val distances = toListOfLongs(input[1])
    val races = arrayListOf<Race>()
    for (i in distances.indices) {
        races.add(Race(times[i], distances[i]))
    }
    return races
}

fun toListOfLongs(string: String) =
    string.split(":")[1]
        .split(" ")
        .filter { it.isNotEmpty() }
        .map { it.toLong() }

fun parseRace(input: List<String>): Race {
    val time = toLong(input[0])
    val distance = toLong(input[1])
    return Race(time, distance)
}

private fun toLong(string: String) = string.split(":")[1].replace(" ", "").toLong()