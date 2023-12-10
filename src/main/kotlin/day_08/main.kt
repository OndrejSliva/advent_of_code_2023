package day_08

fun main() {
    val lines =
        object {}.javaClass.classLoader.getResourceAsStream("advent_of_code_2023_08.txt")?.bufferedReader()?.readLines()
            ?.stream()?.toList()!!

    val steps = parseSteps(lines[0])
    val navigationRows = lines.stream().skip(2).toList()
    val map = parseNavigationMap(navigationRows)
    val countToZZZ = map.countSteps(steps, "AAA", "ZZZ")
    val startingPositions = map.navigationRows.filter { it.position[2] == 'A' }.map { it.position }.toList()
    val endPositions = map.navigationRows.filter { it.position[2] == 'Z' }.map { it.position }.toList()

    val loopSizes = map.countSteps3(steps, startingPositions, endPositions)
    val allToZ = loopSizes.reduce { acc, value -> lcm(acc, value) }
    println(countToZZZ)
    println(allToZ)
}

fun lcm(a: Long, b: Long): Long {
    return a * b /  gcd (a, b)
}

fun gcd(a: Long, b: Long): Long {
    return if (b == 0L) {
        a
    } else {
        gcd(b, a % b)
    }
}

fun parseSteps(stepsString: String): List<StepDirection> {
    val result = arrayListOf<StepDirection>()
    stepsString.forEach {
        val direction = if(it == 'L') StepDirection.LEFT else StepDirection.RIGHT
        result.add(direction)
    }
    return result
}

fun parseNavigationRow(row: String): NavigationRow {
    val positionAndDestinations = row.split("=")
    val position = positionAndDestinations[0].trim()
    val destinations = positionAndDestinations[1].replace("(", "")
        .replace(")", "")
        .replace(" ", "")
        .split(",")
    val leftDestination = destinations[0]
    val rightDestination = destinations[1]
    return NavigationRow(position, leftDestination, rightDestination)
}

fun parseNavigationMap(lines: List<String>): NavigationMap {
    val navigationRows = lines.map { parseNavigationRow(it) }.toList()
    return NavigationMap(navigationRows)
}