package xyz.ksharma.krail.nav

import androidx.navigation.NavController
import kotlinx.serialization.Serializable
import javax.inject.Inject

internal class RealNavigator @Inject constructor(
    private val navController: NavController,
) : Navigator {

    override fun goTo(screen: Screen) {
        navController.navigate(route = screen)
    }
}

@Serializable
sealed class Screen

@Serializable
data object ScreenA : Screen()

@Serializable
data object ScreenB : Screen()
