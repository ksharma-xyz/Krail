/*
package xyz.ksharma.krail.trip.planner.ui.viewmodel
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.reflect.KClass

class CommonViewModelFactory<VM : ViewModel>(
    private val viewModelClass: KClass<VM>,
    private val creator: () -> VM
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModelClass.java)) {
            @Suppress("UNCHECKED_CAST")
            return creator() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
inline fun <reified VM : ViewModel> composeViewModel(noinline creator: () -> VM): VM {
    val factory = CommonViewModelFactory(VM::class, creator)
    return viewModel(factory = factory)
}
*/
