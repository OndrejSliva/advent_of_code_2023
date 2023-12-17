package day_15

fun main() {
    val inputLine =
        object {}.javaClass.classLoader.getResourceAsStream("advent_of_code_2023_15.txt")?.bufferedReader()?.readLines()
            ?.stream()?.limit(1)?.toList()!![0]

    val steps = inputLine.split(",")
    val sumOfHashes = steps.sumOf { hashString(it) }
    println(sumOfHashes)
    val focusingPower = countFocusingPower(steps)
    println(focusingPower)
}

fun hashString(stringToHash: String): Int = stringToHash.fold(0) { acc, c -> ((acc + c.code) * 17) % 256 }

fun countFocusingPower(steps: List<String>): Int {
    val map = (0..255).associateWith { arrayListOf<String>() }
    steps.forEach { step ->
        val operation = if (step.contains("-")) "-" else "="
        val label = if (operation == "=") step.substring(0, step.length - 2) else step.substring(0, step.length - 1)
        val hash = hashString(label)
        val box = map.getValue(hash)
        val valueIdx = box.indexOfFirst { it.contains(label) }
        if (operation == "=") {
            if (valueIdx == -1) {
                box.add(step)
            } else {
                box[valueIdx] = step
            }
        } else {
            if (valueIdx != -1) {
                box.removeAt(valueIdx)
            }
        }
    }

    return map.map { box ->
        (box.key + 1) * box.value.mapIndexed { index, lens ->
            val focalLength = lens.split("=")[1].toInt()
            focalLength * (index + 1)
        }.sum()
    }.sum()
}