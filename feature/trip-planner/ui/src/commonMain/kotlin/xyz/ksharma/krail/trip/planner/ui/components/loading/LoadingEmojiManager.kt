package xyz.ksharma.krail.trip.planner.ui.components.loading

import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.todayIn
import kotlin.random.Random

object LoadingEmojiManager {

    private val commonEmojiList = persistentListOf(
        "🛴",
        "🛹",
        "🚀",
        "🛶",
        "\uD83D\uDC2C", // Dolphin
        "⏰", // Alarm Clock
        "\uD83D\uDEFA", // Auto
        "\uD83D\uDEB2", // Bicycle
    )

    private val rareEmojiList = persistentListOf("🐦‍🔥")

    private val festivalEmojiMap = mapOf(
        FestivalType.CHRISTMAS to listOf("🎄", "🎅", "☃\uFE0F"),
        FestivalType.CHRISTMAS to listOf("🎁"),
        FestivalType.NEW_YEAR to listOf("🎉"),
        FestivalType.NEW_YEAR_EVE to listOf("🎆"),
        FestivalType.ANZAC_DAY to listOf(
            "🌺", // Flower
            "🇦🇺", // Australia Flag
            "\uD83C\uDF96\uFE0F", // Military Medal
            "\uD83C\uDF3F" // Herb Rosemary
        ),
        FestivalType.EASTER to listOf("🐰", "🐣", "🥚"),
        FestivalType.VALENTINES_DAY to listOf("❤️", "🌹"),
        FestivalType.HALLOWEEN to listOf("🎃", "👻"),
        FestivalType.CHINESE_NEW_YEAR to listOf("🧧"),
    )

    // TODO - test logic
    data class MonthDay(val month: Int, val dayOfMonth: Int) {
        companion object {
            fun of(month: Int, dayOfMonth: Int) = MonthDay(month, dayOfMonth)
        }
    }

    private val knownFestivalDates = mapOf(
        FestivalType.CHRISTMAS to MonthDay.of(12, 25),
        FestivalType.BOXING_DAY to MonthDay.of(12, 26),
        FestivalType.NEW_YEAR_EVE to MonthDay.of(12, 31),
        FestivalType.NEW_YEAR to MonthDay.of(1, 1),
        FestivalType.VALENTINES_DAY to MonthDay.of(2, 14),
        FestivalType.ANZAC_DAY to MonthDay.of(4, 25),
    )

    internal fun getRandomEmoji(overrideEmoji: String? = null): String {
        if (overrideEmoji != null) return overrideEmoji

        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val festivalEmoji = knownFestivalDates.entries
            .firstOrNull { it.value.month == today.month.number && it.value.dayOfMonth == today.dayOfMonth }
            ?.let { festivalEmojiMap[it.key]?.randomOrNull() }
        if (festivalEmoji != null) return festivalEmoji

        val randomValue = Random.nextInt(100)
        return when {
            randomValue < 99 -> commonEmojiList.random() // 99% chance for common emojis
            else -> rareEmojiList.random() // 1% chance for the rare emoji
        }
    }
}
