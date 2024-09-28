package xyz.ksharma.krail.design.system.components

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import org.junit.Rule
import org.junit.Test

class DemoUiTest {

    @get:Rule
    val paparazziRule: Paparazzi = Paparazzi(
        theme = "android:Theme.MaterialComponents.Light.NoActionBar",
        deviceConfig = DeviceConfig.PIXEL,
        renderingMode = SessionParams.RenderingMode.SHRINK,
    )

    @Test
    fun screen() {
/*
        paparazziRule.snapshot {
            // UI here
        }
*/
    }
}
