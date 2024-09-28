package xyz.ksharma.krail.design.system.preview

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import androidx.compose.ui.tooling.preview.Preview

/**
 * A MultiPreview annotation for displaying a component using light and dark themes.
 */
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL, showBackground = true)
annotation class ComponentPreviewLightDark

/**
 * A MultiPreview annotation for displaying a component using light and dark themes along with
 * large font size.
 */
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@ComponentPreviewLightDark
@Preview(name = "Large Font", fontScale = 2f, showBackground = true)
annotation class ComponentPreviews
