package space.iqbalsyafiq.movieapp.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import space.iqbalsyafiq.core.domain.usecase.MovieInteractor
import space.iqbalsyafiq.core.domain.usecase.MovieUseCase
import space.iqbalsyafiq.movieapp.detail.DetailViewModel
import space.iqbalsyafiq.movieapp.home.HomeViewModel

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}