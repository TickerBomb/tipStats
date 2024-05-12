package di

import CoroutinesComponent
import org.koin.dsl.module

val provideCoroutineModule = module {
    single<CoroutinesComponent> { ApplicationComponent.coreComponent }
}
