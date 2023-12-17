package day_11

fun main() {
    val lines =
        object {}.javaClass.classLoader.getResourceAsStream("advent_of_code_2023_11.txt")?.bufferedReader()?.readLines()
            ?.stream()?.toList()!!

    val expandedGalaxy = expandGalaxy(lines)
    val expandedGalaxyIndexes = getGalaxyIndexes(expandedGalaxy)
    val totalDistanceBetweenAllGalaxies = countDistancesBetweenAllGalaxies(expandedGalaxyIndexes)
    println(totalDistanceBetweenAllGalaxies)
    val galaxyIndexes = getGalaxyIndexes(lines)
    val totalDistanceBetweenAllGalaxiesWithMultiplier = countDistancesBetweenAllGalaxiesWithEmptyMultiplier(lines, galaxyIndexes, 1000000)
    println(totalDistanceBetweenAllGalaxiesWithMultiplier)
}

fun expandGalaxy(galaxy: List<String>): List<String> {
    val expandedGalaxy = arrayListOf<String>()

    val emptyColumnsIndexes = arrayListOf<Int>()
    for (colIdx in galaxy[0].indices) {
        if (galaxy.map { it.toCharArray()[colIdx] }.all { it == '.' }) {
           emptyColumnsIndexes.add(colIdx)
        }
    }

    galaxy.forEach { galaxyRow ->
        if(galaxyRow.all { it == '.' }) {
            expandedGalaxy.add(galaxyRow)
        }

        expandedGalaxy.add(galaxyRow)
    }

    var shift = 0
    for (colIdx in galaxy[0].indices) {
        if (galaxy.map { it.toCharArray()[colIdx] }.all { it == '.' }) {
            expandedGalaxy.forEachIndexed { index, value ->
                val newRow = "${value.substring(0, colIdx + shift)}.${value.substring(colIdx + shift)}"
                expandedGalaxy[index] = newRow
            }
            shift++
        }
    }

    return expandedGalaxy
}

fun getGalaxyIndexes(expandedGalaxy: List<String>) = expandedGalaxy
    .mapIndexed { rowIndex, galaxyRow ->
        mapGalaxyRowToIndexes(galaxyRow, rowIndex)
    }.flatten()

private fun mapGalaxyRowToIndexes(
    galaxyRow: String,
    rowIndex: Int
) = galaxyRow.mapIndexed { charIndex, char -> charIndex to char }
    .filter { it.second != '.' }
    .map { GalaxyIndex(rowIndex, it.first) }

fun countDistancesBetweenAllGalaxies(expandedGalaxyGalaxyIndexes: List<GalaxyIndex>): Int {
    var totalDistance = 0
    for (i in expandedGalaxyGalaxyIndexes.indices) {
        for (j in i until expandedGalaxyGalaxyIndexes.size) {
            totalDistance += expandedGalaxyGalaxyIndexes[i].distance(expandedGalaxyGalaxyIndexes[j])
        }
    }
    return totalDistance
}

fun getEmptyLinesIndexes(galaxy: List<String>): List<Int> = galaxy
    .mapIndexed { rowIndex, galaxyRow -> rowIndex to galaxyRow }
    .filter { it.second.all { it == '.' } }
    .map { it.first }
    .toList()

fun getEmptyColumnsIndexes(smallGalaxy: List<String>): List<Int> =
    smallGalaxy[0].mapIndexed { columnIndex, _ -> columnIndex }
        .filter { columnIndex -> smallGalaxy.all { it.toCharArray()[columnIndex] == '.' } }
        .toList()

fun countDistancesBetweenAllGalaxiesWithEmptyMultiplier(galaxy: List<String>, galaxyIndexes: List<GalaxyIndex>, multiplier: Int): Long {
    var totalDistance = 0L
    val emptyLinesIndexes = getEmptyLinesIndexes(galaxy)
    val emptyColumnIndexes = getEmptyColumnsIndexes(galaxy)
    for (i in galaxyIndexes.indices) {
        for (j in i until galaxyIndexes.size) {
            totalDistance += galaxyIndexes[i].distance(galaxyIndexes[j], emptyLinesIndexes, emptyColumnIndexes, multiplier)
        }
    }
    return totalDistance
}