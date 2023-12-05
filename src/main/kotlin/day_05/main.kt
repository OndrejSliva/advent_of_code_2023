package day_05

fun main() {
    val lines =
        object {}.javaClass.classLoader.getResourceAsStream("advent_of_code_2023_05.txt")?.bufferedReader()?.readLines()
            ?.stream()?.toList()!!

    val seeds = parseSeeds(lines[0])
    val atlas = parseTranslationAtlas(lines.stream().skip(2).toList())

    val lowestLocation = seeds.map {
        atlas.translate("seed", "location", it)
    }.min()


    val numbers = lines[0].split(": ")[1].split(" ").map { it.toLong() }

    //there is probably some better algorithm then this
    var count: Long = 0
    var lowestLocation2 = lowestLocation
    for (idx in numbers.indices step 2) {
        val range = numbers[idx + 1]
        for (i in 0 until range) {
            count += 1
            if (count % 10000000 == 0L) {
                println("$count/2201752837, ${count.toDouble() / 2201752837.0}")
            }
            val translation = atlas.translate("seed", "location", numbers[idx] + i)
            if (translation < lowestLocation2) {
                lowestLocation2 = translation
            }
        }
    }
    println(count)

    println(lowestLocation)
    println(lowestLocation2)
}

fun parseTranslationRow(string: String): TranslationRow {
    val splitted = string.split(' ')
    return TranslationRow(
        splitted[0].toLong(),
        splitted[1].toLong(),
        splitted[2].toLong()
    )
}

fun parseTranslationMap(rows: List<String>): TranslationMap {
    val fromAndTo = rows[0].split(' ')[0].split('-')
    val from = fromAndTo[0]
    val to = fromAndTo[2]
    val transactionRows = rows.stream().skip(1).map {
        parseTranslationRow(it)
    }.toList()

    return TranslationMap(
        from,
        to,
        transactionRows
    )
}

fun parseSeeds(string: String) = string
    .split(": ")[1]
    .split(" ")
    .map { it.toLong() }

fun parseSeedRanges(string: String): List<Long> {
    val result = arrayListOf<Long>()
    val numbers = string.split(": ")[1].split(" ").map { it.toLong() }

    for (idx in numbers.indices step 2) {
        val range = numbers[idx + 1]
        for (i in 0 until range) {
            result.add(numbers[idx] + i)
        }
    }

    return result
}

fun parseTranslationAtlas(rows: List<String>): TranslationAtlas {
    val currentRows = arrayListOf<String>()
    val translationMaps = arrayListOf<TranslationMap>()
    rows.forEach {
        if (it.isEmpty()) {
            val translationMap = parseTranslationMap(currentRows)
            translationMaps.add(translationMap)
            currentRows.clear()
        } else {
            currentRows.add(it)
        }
    }
    if (currentRows.size > 0) {
        val translationMap = parseTranslationMap(currentRows)
        translationMaps.add(translationMap)
        currentRows.clear()
    }

    return TranslationAtlas(translationMaps)
}