package day_05

data class TranslationRow(
    val destinationStart: Long,
    val sourceStart: Long,
    val length: Long
) {
    fun translate(source: Long): Long {
        val sourceEnd = sourceStart + length - 1
        if (source in sourceStart..sourceEnd) {
            return destinationStart - sourceStart + source
        }
        return source
    }
}
