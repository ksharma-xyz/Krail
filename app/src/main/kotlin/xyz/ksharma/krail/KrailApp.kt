package xyz.ksharma.krail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.ksharma.krail.design.system.components.Text
import xyz.ksharma.krail.design.system.theme.KrailTheme
import xyz.ksharma.krail.nav.ScreenA

@Composable
internal fun KrailApp(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.DemoPage.name,
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding()
    ) {
        composable(route = AppScreen.DemoPage.name) {

        }
    }
}


@Composable
fun ScreenA(modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .fillMaxSize()
        .background(color = KrailTheme.colors.primaryContainer)
        .padding(start = 16.dp, end = 16.dp)) {
        Text("A Screen", style = KrailTheme.typography.bodyLarge)
    }
}

@Composable
fun ScreenB(modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .fillMaxSize()
        .background(color = KrailTheme.colors.secondaryContainer)
        .padding(start = 16.dp, end = 16.dp)) {
        Text("B Screen", style = KrailTheme.typography.bodyLarge)
    }
}
