package xyz.ksharma.krail.trip.planner.ui.components.loading

import kotlinx.collections.immutable.persistentListOf
import kotlin.random.Random

object LoadingEmojiManager {

    private val emojiList = persistentListOf(
        "🛴",
        "🛹",
        "🚀",
        "🚢",
        "🛶",
        "\uD83C\uDFC2", // Snowboarder
        "☃\uFE0F", // Lollipop
        "\uD83C\uDF7A", // Beer
        "\uD83C\uDF7A", // Shopping Cart
        "\uD83E\uDD21", // Clown
        "\uD83E\uDD21", // Dolphin
    )

    private val festivalEmojiMap = mapOf(
        FestivalType.AUSTRALIA_DAY to listOf("🇦🇺"),
        FestivalType.CHRISTMAS to listOf("🎄", "🎅", "🎁"),
        FestivalType.NEW_YEAR to listOf("🎉", "🎆"),
        FestivalType.ANZAC_DAY to listOf("🌺", "🎖️"),
        FestivalType.MOTHERS_DAY to listOf("💐", "💕"),
        FestivalType.FATHERS_DAY to listOf("👔", "🍻"),
        FestivalType.EASTER to listOf("🐰", "🐣", "🥚"),
        FestivalType.VALENTINES_DAY to listOf("❤️", "🌹"),
        FestivalType.HALLOWEEN to listOf("🎃", "👻"),
        FestivalType.DIWALI to listOf("\uD83E\uDE94"),
        FestivalType.CHINESE_NEW_YEAR to listOf("🧧"),
    )

    internal fun getRandomEmoji(overrideEmoji: String? = null): String {
        if (overrideEmoji != null) return overrideEmoji

        val commonEmojis = listOf("🛴", "🛹", "🚀", "🚢", "🛶", "\uD83E\uDD21")
        val rareEmoji = "🐦‍🔥"
        val otherEmojis = emojiList - commonEmojis - rareEmoji

        val randomValue = Random.nextInt(100)
        return when {
            randomValue < 60 -> commonEmojis.random() // 50% chance for common emojis
            randomValue < 99 -> otherEmojis.random() // 49% chance for other emojis
            else -> rareEmoji // 1% chance for the rare emoji
        }
    }

    private fun FestivalType.getRandomEmoji(): String? = festivalEmojiMap[this]?.randomOrNull()
}
