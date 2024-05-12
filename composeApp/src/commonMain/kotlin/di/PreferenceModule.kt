package di

import ApplicationComponent
import domain.AppPreferences
import org.koin.dsl.module

val providePreferenceModule = module {
    single<AppPreferences> { ApplicationComponent.coreComponent.appPreferences }
}
