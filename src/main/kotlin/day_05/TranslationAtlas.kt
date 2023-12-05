package day_05

data class TranslationAtlas(
    val translationMaps: List<TranslationMap>
) {
    fun translate(from: String, to: String, value: Long): Long {
        var currentDestinationType = from
        var currentChanges = 0
        var currentValue = value
        while (currentDestinationType != to && currentChanges < 10) {
            currentChanges++
            val translationMap = translationMaps.first { it.from == currentDestinationType }
            currentValue = translationMap.translate(currentValue)
            currentDestinationType = translationMap.to
        }
        return currentValue
    }
}
