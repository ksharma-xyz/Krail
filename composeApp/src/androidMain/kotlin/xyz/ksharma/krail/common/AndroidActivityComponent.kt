package xyz.ksharma.krail.common

import android.app.Activity
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

//@ActivityScope
@Component
abstract class AndroidActivityComponent(
    @get:Provides val activity: Activity,
    @Component val applicationComponent: AndroidApplicationComponent,
) {
    companion object
}
