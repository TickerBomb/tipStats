package di

import org.koin.dsl.module
import viewmodel.MainViewModel

val provideviewModelModule = module {
    single {
        MainViewModel(get(), get(), get())
    }
}
