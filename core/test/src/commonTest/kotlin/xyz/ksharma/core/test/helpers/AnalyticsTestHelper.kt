package xyz.ksharma.core.test.helpers

import xyz.ksharma.core.test.fakes.FakeAnalytics
import xyz.ksharma.krail.core.analytics.Analytics
import xyz.ksharma.krail.core.analytics.event.AnalyticsEvent
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

object AnalyticsTestHelper {

    fun assertScreenViewEventTracked(
        fakeAnalytics: Analytics,
        expectedScreenName: String,
    ) {
        assertIs<FakeAnalytics>(fakeAnalytics)
        assertTrue(fakeAnalytics.isEventTracked("view_screen"))
        val event = fakeAnalytics.getTrackedEvent("view_screen")
        assertIs<AnalyticsEvent.ScreenViewEvent>(event)
        assertEquals(expectedScreenName, event.screen.name)
    }
}
