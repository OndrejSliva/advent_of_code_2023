package day_16

fun main() {
    val lines =
        object {}.javaClass.classLoader.getResourceAsStream("advent_of_code_2023_16.txt")?.bufferedReader()?.readLines()
            ?.stream()?.toList()!!

    val energizedFields = countEnergizedFields(lines, Beam(MapIndex(0, 0), Direction.RIGHT))
    println(energizedFields)
    val mostEnergizedFields = countMostEnergizedFields(lines)
    println(mostEnergizedFields)
}

fun countEnergizedFields(map: List<String>, startingBeam: Beam): Int {
    val mapHeight = map.size
    val mapWidth = map[0].length
    var beams = listOf(startingBeam)
    val visited = hashSetOf<MapIndex>()
    val visitedWithDirections = hashSetOf<String>()

    while (beams.isNotEmpty()) {
        beams = beams.map {
            val beamPosition = it.position
            visited.add(beamPosition)
            val character = map[beamPosition.rowIdx].toCharArray()[beamPosition.colIdx]
            it.move(character)
        }
            .flatten()
            .filter { !it.position.isOut(mapWidth, mapHeight) }
            .filter {
                val str = "${it.position.rowIdx},${it.position.colIdx},${it.direction}"
                !visitedWithDirections.contains(str)
            }

        beams.forEach {
            val str = "${it.position.rowIdx},${it.position.colIdx},${it.direction}"
            visitedWithDirections.add(str)
        }
    }

    return visited.size
}

fun countMostEnergizedFields(map: List<String>): Int {
    val leftBeams = (map.indices).map { Beam(MapIndex(it, map[0].lastIndex), Direction.LEFT) }
    val rightBeams = (map.indices).map { Beam(MapIndex(it, 0), Direction.RIGHT) }
    val upBeams = (map[0].indices).map { Beam(MapIndex(map.lastIndex, it), Direction.UP) }
    val downBeams = (map[0].indices).map { Beam(MapIndex(0, it), Direction.DOWN) }
    val startingBeams = leftBeams + rightBeams + upBeams + downBeams
    return startingBeams.maxOf { countEnergizedFields(map, it) }
}