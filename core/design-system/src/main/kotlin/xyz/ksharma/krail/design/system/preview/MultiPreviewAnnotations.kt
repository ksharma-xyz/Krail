package xyz.ksharma.krail.design.system.preview

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark

/**
 * A MultiPreview annotation for displaying a component using light and dark themes along with
 * large font size.
 */
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@PreviewLightDark
@Preview(name = "Large Font", fontScale = 2f, device = Devices.PIXEL_5, widthDp = 420)
annotation class ComponentPreviews
