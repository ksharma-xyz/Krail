package xyz.ksharma.krail.trip.planner.ui.components.loading

import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.todayIn
import kotlin.random.Random

object LoadingEmojiManager {

    private val emojiList = persistentListOf(
        "🛴",
        "🛹",
        "🚀",
        "🛶",
        "\uD83C\uDFC2", // Snowboarder
        "\uD83D\uDC2C", // Dolphin
        "\uD83E\uDD21", // Clown,
        "\uD83D\uDEFA", // Auto
        "\uD83D\uDEB2", // Bicycle
    )

    private val festivalEmojiMap = mapOf(
        FestivalType.AUSTRALIA_DAY to listOf("🇦🇺"),
        FestivalType.CHRISTMAS to listOf("🎄", "🎅", "🎁", "☃\uFE0F"),
        FestivalType.NEW_YEAR to listOf("🎉", "🎆"),
        FestivalType.ANZAC_DAY to listOf("🌺", "🇦🇺"),
        FestivalType.MOTHERS_DAY to listOf("💐", "💕"),
        FestivalType.FATHERS_DAY to listOf("👔", "🍻"),
        FestivalType.EASTER to listOf("🐰", "🐣", "🥚"),
        FestivalType.VALENTINES_DAY to listOf("❤️", "🌹"),
        FestivalType.HALLOWEEN to listOf("🎃", "👻"),
        FestivalType.DIWALI to listOf("\uD83E\uDE94"),
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
        FestivalType.NEW_YEAR to MonthDay.of(1, 1),
        FestivalType.VALENTINES_DAY to MonthDay.of(2, 14),
        FestivalType.AUSTRALIA_DAY to MonthDay.of(1, 26),
        FestivalType.ANZAC_DAY to MonthDay.of(4, 25),
    )

    internal fun getRandomEmoji(overrideEmoji: String? = null): String {
        if (overrideEmoji != null) return overrideEmoji

        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val festivalEmoji = knownFestivalDates.entries
            .firstOrNull { it.value.month == today.month.number && it.value.dayOfMonth == today.dayOfMonth }
            ?.let { festivalEmojiMap[it.key]?.randomOrNull() }
        if (festivalEmoji != null) return festivalEmoji

        val commonEmojis = listOf("🛴", "🛹", "🚀", "🛶", "\uD83E\uDD21", "\uD83D\uDC2C")
        val rareEmoji = "🐦‍🔥"
        val otherEmojis = emojiList - commonEmojis - rareEmoji

        val randomValue = Random.nextInt(100)
        return when {
            randomValue < 60 -> commonEmojis.random() // 50% chance for common emojis
            randomValue < 99 -> otherEmojis.random() // 49% chance for other emojis
            else -> rareEmoji // 1% chance for the rare emoji
        }
    }
}
