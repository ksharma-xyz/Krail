package xyz.ksharma.krail.core.datetime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

data class DateTimePickerInfo(
    val date: String,
    val hour: Int,
    val minute: Int,
)

@Composable
fun rememberCurrentDateTime(): DateTimePickerInfo {
    val currentTime = Clock.System.now()
    val currentDateTime = remember {
        currentTime.toLocalDateTime(TimeZone.currentSystemDefault())
    }

    // Formatting the date
    val formattedDate = remember(currentDateTime.date) {
        formatDate(currentDateTime.date)
    }

    // Extracting hour and minute in 12-hour format
    val hour = remember(currentDateTime.time.hour) {
        (currentDateTime.time.hour % 12).takeIf { it != 0 } ?: 12 // Convert 24-hour to 12-hour
    }
    val minute = remember(currentDateTime.time.minute) {
        currentDateTime.time.minute
    }

    return DateTimePickerInfo(
        date = formattedDate,
        hour = hour,
        minute = minute
    )
}

private fun formatDate(date: LocalDate): String {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val tomorrow = today.plus(1, DateTimeUnit.DAY)

    return when (date) {
        today -> "Today"
        tomorrow -> "Tomorrow"
        else -> {
            val dayOfWeek = date.dayOfWeek.name.substring(0, 3) // Short day name (e.g., Mon, Tue)
            val month = date.month.name.substring(0, 3) // Short month name (e.g., Jan, Feb)
            "$dayOfWeek ${date.dayOfMonth} ${month.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }}"
        }
    }
}
