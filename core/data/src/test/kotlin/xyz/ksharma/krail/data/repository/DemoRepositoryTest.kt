package xyz.ksharma.krail.data.repository

import android.content.Context
import android.content.res.AssetManager
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.ByteArrayInputStream

class DemoRepositoryTest {

    private val context: Context = mock()
    private val mockDispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
    private val mockAssets = mock<AssetManager> {
        on { open("demo.json") } doReturn (ByteArrayInputStream("".toByteArray()))
    }

    private lateinit var demoRepositoryImpl: DemoRepositoryImpl

    /**
     * This test may not work as it is a bit challenging to mock Context in Junit.
     * Ideally we would use Retrofit to get the data in which case the Service interface
     * can be mocked.
     */
    @Test
    fun test() = runTest {
        whenever(context.assets).thenReturn(mockAssets)
        whenever(context.assets.open("demo.json")).thenReturn(ByteArrayInputStream(jsonStr.toByteArray()))
        whenever(mockAssets.open("demo.json")).thenReturn(ByteArrayInputStream(jsonStr.toByteArray()))

        demoRepositoryImpl = DemoRepositoryImpl(
            ioDispatcher = mockDispatcher,
            context = context
        )

        val data = demoRepositoryImpl.fetchData()
        data.test {
            val item = awaitItem()
            assertThat(item.data.size).isEqualTo(3)
            awaitComplete()
        }
    }

    companion object {

        val jsonStr ="{\"data\":[{\"id\":\"111\",\"value\":\"ABC\"},{\"id\":\"112\",\"value\":\"DEF\"},{\"id\":\"113\",\"value\":\"XYZ\"}]}"
    }
}
