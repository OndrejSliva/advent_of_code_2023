package day_04

import kotlin.math.pow

data class Card(
    val id: Int,
    val winning: Set<Int>,
    val myNumbers: Set<Int>
) {
    fun score(): Int {
        val same = same()
        return if(same == 0) 0 else 2.0.pow(same - 1).toInt()
    }

    fun same(): Int = winning.intersect(myNumbers).size
}
