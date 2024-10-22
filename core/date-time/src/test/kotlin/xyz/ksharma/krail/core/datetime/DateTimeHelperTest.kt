package xyz.ksharma.krail.core.datetime

import com.google.common.truth.Truth.assertThat
import kotlinx.datetime.Instant
import kotlin.test.Test

class DateTimeHelperTest {

    @Test
    fun testCalculateTimeDifference() {
        val difference = DateTimeHelper.calculateTimeDifferenceFromNow(
            utcDateString = "2024-10-07T09:00:00Z",
            now = Instant.parse("2024-10-07T08:20:00Z"),
        )
        assertThat(difference.inWholeMinutes).isEqualTo(40L)
    }

    @Test
    fun testCalculateTimeDifferenceNextDay() {
        val difference = DateTimeHelper.calculateTimeDifferenceFromNow(
            utcDateString = "2024-10-08T09:00:00Z",
            now = Instant.parse("2024-10-07T08:20:00Z"),
        )
        assertThat(difference.inWholeMinutes).isEqualTo(1480L)
    }

    @Test
    fun testCalculateTimeDifferencePreviousDay() {
        val difference = DateTimeHelper.calculateTimeDifferenceFromNow(
            utcDateString = "2024-10-06T09:00:00Z",
            now = Instant.parse("2024-10-07T08:20:00Z"),
        )
        assertThat(difference.inWholeMinutes).isEqualTo(-1400L)
    }
}
