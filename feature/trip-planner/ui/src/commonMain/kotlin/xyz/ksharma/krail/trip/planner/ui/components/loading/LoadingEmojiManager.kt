package xyz.ksharma.krail.trip.planner.ui.components.loading

import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.todayIn
import kotlin.random.Random

// TODO - Add better logic
//  1. should be able to create range of dates with exhaustive statements.
//  2. Should be able to fetch festival dates / emoji from remote config rather than hard code.
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
        FestivalType.CHRISTMAS to listOf("🎄", "\uD83C\uDF85", "❄\uFE0F", "🎁"),
        FestivalType.NEW_YEAR to listOf("🎉"),
        FestivalType.NEW_YEAR_EVE to listOf("🎆"),
        FestivalType.ANZAC_DAY to listOf(
            "🌺", // Flower
            "🇦🇺", // Australia Flag
            "\uD83C\uDF96\uFE0F", // Military Medal
            "\uD83C\uDF3F" // Herb Rosemary
        ),
        FestivalType.EASTER to listOf("🐰", "🐣", "🥚"),
        FestivalType.HALLOWEEN to listOf("🎃", "👻"),
        FestivalType.CHINESE_NEW_YEAR to listOf("🧧"),

        FestivalType.ROSE_DAY to listOf("🌹"),
        FestivalType.PROPOSE_DAY to listOf("💍", "💞", "💌"),
        FestivalType.CHOCOLATE_DAY to listOf("🍫", "💞"),
        FestivalType.TEDDY_DAY to listOf("🧸", "💖"),
        FestivalType.PROMISE_DAY to listOf("🤝", "💖"),
        FestivalType.HUG_DAY to listOf("🤗", "💖"),
        FestivalType.KISS_DAY to listOf("💋", "💖", "❤️", "💞"),
        FestivalType.VALENTINES_DAY to listOf("❤️", "🌹", "💖"),

        FestivalType.HOLI to listOf("🎨", "🌈", "🎉", "🎈"),

        FestivalType.AUSTRALIA_DAY to listOf("🇦🇺", "🎉", "🎆"),
        FestivalType.EID to listOf("🌙", "🕌", "🎉", "🎁"),
        FestivalType.MARDI_GRAS to listOf("🏳️‍🌈", "💃", "🎭", "🪩", "🎉"),
        FestivalType.VIVID_SYDNEY to listOf("🎆", "🌈", "🌟", "✨"),
    )

    // TODO - test logic add UT
    data class MonthDay(val month: Int, val dayOfMonth: Int) {
        companion object {
            fun of(month: Int, dayOfMonth: Int) = MonthDay(month, dayOfMonth)
        }
    }

    private val knownFestivalDates = mapOf(
        // Sure dates
        FestivalType.CHRISTMAS to MonthDay.of(12, 25),
        FestivalType.BOXING_DAY to MonthDay.of(12, 26),
        FestivalType.NEW_YEAR_EVE to MonthDay.of(12, 31),
        FestivalType.NEW_YEAR to MonthDay.of(1, 1),
        FestivalType.ANZAC_DAY to MonthDay.of(4, 25),

        // Valentines day
        FestivalType.ROSE_DAY to MonthDay.of(2, 7),
        FestivalType.PROPOSE_DAY to MonthDay.of(2, 8),
        FestivalType.CHOCOLATE_DAY to MonthDay.of(2, 9),
        FestivalType.TEDDY_DAY to MonthDay.of(2, 10),
        FestivalType.PROMISE_DAY to MonthDay.of(2, 11),
        FestivalType.HUG_DAY to MonthDay.of(2, 12),
        FestivalType.KISS_DAY to MonthDay.of(2, 13),
        FestivalType.VALENTINES_DAY to MonthDay.of(2, 14),

        FestivalType.AUSTRALIA_DAY to MonthDay.of(1, 26),

        // Can change dates

        // Chinese New Year 2025
        FestivalType.CHINESE_NEW_YEAR to MonthDay.of(1, 29),
        FestivalType.CHINESE_NEW_YEAR to MonthDay.of(1, 30),
        FestivalType.CHINESE_NEW_YEAR to MonthDay.of(1, 31),
        FestivalType.CHINESE_NEW_YEAR to MonthDay.of(2, 1),
        FestivalType.CHINESE_NEW_YEAR to MonthDay.of(2, 2),
        FestivalType.CHINESE_NEW_YEAR to MonthDay.of(2, 3),
        FestivalType.CHINESE_NEW_YEAR to MonthDay.of(2, 4),
        FestivalType.CHINESE_NEW_YEAR to MonthDay.of(2, 5),
        FestivalType.CHINESE_NEW_YEAR to MonthDay.of(2, 6),

        FestivalType.HOLI to MonthDay.of(3, 14),
        FestivalType.EID to MonthDay.of(3, 30),
        FestivalType.EID to MonthDay.of(3, 31),

        // Mardi Gras 2025
        FestivalType.MARDI_GRAS to MonthDay.of(2, 15),
        FestivalType.MARDI_GRAS to MonthDay.of(2, 16),
        FestivalType.MARDI_GRAS to MonthDay.of(2, 17),
        FestivalType.MARDI_GRAS to MonthDay.of(2, 18),
        FestivalType.MARDI_GRAS to MonthDay.of(2, 19),
        FestivalType.MARDI_GRAS to MonthDay.of(2, 20),
        FestivalType.MARDI_GRAS to MonthDay.of(2, 21),
        FestivalType.MARDI_GRAS to MonthDay.of(2, 22),
        FestivalType.MARDI_GRAS to MonthDay.of(2, 23),
        FestivalType.MARDI_GRAS to MonthDay.of(2, 24),
        FestivalType.MARDI_GRAS to MonthDay.of(2, 25),
        FestivalType.MARDI_GRAS to MonthDay.of(2, 26),
        FestivalType.MARDI_GRAS to MonthDay.of(2, 27),
        FestivalType.MARDI_GRAS to MonthDay.of(2, 28),
        FestivalType.MARDI_GRAS to MonthDay.of(3, 1),
        FestivalType.MARDI_GRAS to MonthDay.of(3, 2),

        // Easter 2025
        FestivalType.EASTER to MonthDay.of(4, 20),

        // Vivid Sydney 2025
        FestivalType.VIVID_SYDNEY to MonthDay.of(5, 23),
        FestivalType.VIVID_SYDNEY to MonthDay.of(5, 24),
        FestivalType.VIVID_SYDNEY to MonthDay.of(5, 25),
        FestivalType.VIVID_SYDNEY to MonthDay.of(5, 26),
        FestivalType.VIVID_SYDNEY to MonthDay.of(5, 27),
        FestivalType.VIVID_SYDNEY to MonthDay.of(5, 28),
        FestivalType.VIVID_SYDNEY to MonthDay.of(5, 29),// till 14 june
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
