package day_01

import java.util.regex.Pattern

fun main() {
    var sum = 0
    var sum2 = 0
    object {}.javaClass.classLoader.getResourceAsStream("advent_of_code_2023_01.txt")?.bufferedReader()?.readLines()?.stream()?.forEach{
        sum += getCalibrationValue(it)
        sum2 += getCalibrationValueV2(it)
    }

    println(sum)
    println(sum2)
}

fun getCalibrationValue(string: String): Int {
    val digits = string.filter { it.isDigit() }
    return "${digits[0]}${digits[digits.lastIndex]}".toInt()
}

fun getCalibrationValueV2(string: String): Int {
    val matcher = Pattern.compile("(?=(one|two|three|four|five|six|seven|eight|nine|[1-9]))").matcher(string)
    val digits = arrayListOf<String>()
    while (matcher.find()){
        digits.add(matcher.group(1))
    }
    val firstDigit = digitToNumber(digits[0])
    val lastDigit = digitToNumber(digits[digits.lastIndex])
    return "$firstDigit$lastDigit".toInt()
}

fun digitToNumber(string: String) = when(string) {
    "one" -> 1
    "two" -> 2
    "three" -> 3
    "four" -> 4
    "five" -> 5
    "six" -> 6
    "seven" -> 7
    "eight" -> 8
    "nine" -> 9
    else -> string.toInt()
}