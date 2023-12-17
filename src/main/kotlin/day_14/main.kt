package day_14

fun main() {
    val lines =
        object {}.javaClass.classLoader.getResourceAsStream("advent_of_code_2023_14.txt")?.bufferedReader()?.readLines()
            ?.stream()?.toList()!!

    val platform = Platform(lines)
    val northPlatform = platform.movedNorth()
    println(northPlatform.countLoad())
    println(northPlatform.moveCycles(1000000000).countLoad())
}