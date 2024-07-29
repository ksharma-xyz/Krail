package xyz.ksharma.feature1

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.reset
import org.mockito.kotlin.whenever
import xyz.ksharma.krail.domain.DemoUseCase
import xyz.ksharma.krail.model.DemoData
import xyz.ksharma.krail.model.Item

class DemoViewModelTest {

    private var useCase: DemoUseCase = mock()

    private lateinit var viewModel: DemoViewModel

    @Before
    fun setup() {
        reset(useCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun testLoadingState() = runTest {
        // Given
        whenever(useCase()).thenReturn(flowOf())

        // When
        viewModel = DemoViewModel(demoUseCase = useCase)

        // Then
        viewModel.uiState.test {
            val item = awaitItem()
            assertThat(item).isInstanceOf(DemoUiState.Loading::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun testSuccessUiState() = runTest {
        // Given
        whenever(useCase()).thenReturn(flowOf(Result.success(testDemoData)))
        viewModel = DemoViewModel(demoUseCase = useCase)

        // When
        viewModel.getDemoData()

        // Then
        viewModel.uiState.test {
            val item = awaitItem() as DemoUiState.Success
            assertThat(item.demoData.list.size).isEqualTo(2)
            cancelAndIgnoreRemainingEvents()
        }
    }

    companion object {
        private val testDemoData = DemoData(list = listOf(Item("A", "1"), Item("B", "2")))
    }
}
