package day_02

data class TurnColors(
    val red: Int,
    val green: Int,
    val blue: Int,
) {
    fun powerOfCubes() = red * green * blue
}
