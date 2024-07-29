package xyz.ksharma.krail.design.system.ui

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, fontScale = 1.0f, name = "Light Mode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, fontScale = 1.0f, name = "Dark Mode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, fontScale = 2.0f, name ="Large Font Size")
annotation class ComponentPreviews
