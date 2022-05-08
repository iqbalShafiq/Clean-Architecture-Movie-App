package space.iqbalsyafiq.movieapp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import space.iqbalsyafiq.movieapp.core.domain.usecase.MovieUseCase

class HomeViewModel(movieUseCase: MovieUseCase) : ViewModel() {
    val movie = movieUseCase.getAllMovie().asLiveData()
}