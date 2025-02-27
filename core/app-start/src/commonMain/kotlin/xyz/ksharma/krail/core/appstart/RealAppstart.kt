package xyz.ksharma.krail.core.appstart

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class RealAppStart(private val coroutineScope: CoroutineScope) : AppStart {
    override fun start() {
        coroutineScope.launch {

        }
    }
}
