package xyz.ksharma.krail.core.date_time

import com.google.common.truth.Truth.assertThat
import java.time.Instant
import kotlin.test.Test

class DateTimeHelperTest {

    @Test
    fun testCalculateTimeDifference() {
        val fixedInstant = Instant.parse("2024-10-07T09:00:00Z")
        val difference = DateTimeHelper.calculateTimeDifferenceFromNow(
            utcDateString = "2024-10-07T08:20:00Z",
            timeNow = fixedInstant,
        )
        assertThat(difference.toMinutes()).isEqualTo(-40L)
    }
}
