package day_05

data class TranslationMap(
    val from: String,
    val to: String,
    val translations: List<TranslationRow>
) {
    fun translate(number: Long): Long {
        translations.forEach {
            val translation = it.translate(number)
            if (translation != number) {
                return translation
            }
        }
        return number
    }
}
