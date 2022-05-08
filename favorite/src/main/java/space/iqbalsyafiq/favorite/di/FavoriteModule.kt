package space.iqbalsyafiq.favorite.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import space.iqbalsyafiq.favorite.FavoriteViewModel

val favoriteModule = module {
    viewModel { FavoriteViewModel(get()) }
}