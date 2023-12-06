package day_06

data class Race(
    val time: Long,
    val record: Long
) {
    fun getPossibleDistances(): List<Long> {
        val possibleDistances = arrayListOf<Long>()
        for (holdingTime in 0 .. time) {
            val remainingTime = time - holdingTime
            val speed = holdingTime
            val possibleDistance = speed * remainingTime
            possibleDistances.add(possibleDistance)
        }
        return possibleDistances
    }

    fun getBeatingPossibilitiesCount(): Int = getPossibleDistances().count { it > record }
}
