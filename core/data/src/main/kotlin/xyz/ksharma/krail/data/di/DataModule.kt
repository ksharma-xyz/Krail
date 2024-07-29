package  xyz.ksharma.krail.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.ksharma.krail.data.repository.DemoRepository
import xyz.ksharma.krail.data.repository.DemoRepositoryImpl

@InstallIn(SingletonComponent::class)
@Module
abstract class DataModule {

    @Binds
    abstract fun bindDemoRepository(impl: DemoRepositoryImpl): DemoRepository
}
