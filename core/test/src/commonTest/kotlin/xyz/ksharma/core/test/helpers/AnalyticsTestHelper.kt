package xyz.ksharma.core.test.helpers

import xyz.ksharma.core.test.fakes.FakeAnalytics
import xyz.ksharma.krail.core.analytics.Analytics
import xyz.ksharma.krail.core.analytics.event.AnalyticsEvent
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

object AnalyticsTestHelper {

    fun assertAnalyticsEventTracked(
        fakeAnalytics: Analytics,
        expectedEventName: String,
        expectedScreenName: String,
    ) {
        assertIs<FakeAnalytics>(fakeAnalytics)
        assertTrue(fakeAnalytics.isEventTracked(expectedEventName))
        val event = fakeAnalytics.getTrackedEvent(expectedEventName)
        assertIs<AnalyticsEvent.ScreenViewEvent>(event)
        assertEquals(expectedScreenName, event.screen.name)
    }
}
