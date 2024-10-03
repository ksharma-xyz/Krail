package xyz.ksharma.krail.nav

import androidx.navigation.NavController
import javax.inject.Inject

interface Navigator {

    fun goTo(screen: Screen)
}
