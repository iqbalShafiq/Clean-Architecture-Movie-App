package space.iqbalsyafiq.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import space.iqbalsyafiq.core.domain.model.Movie
import space.iqbalsyafiq.core.domain.usecase.MovieUseCase

class FavoriteViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    val favoriteMovie = movieUseCase.getFavoriteMovie().asLiveData()

    fun searchFavoriteMovie(query: String): LiveData<List<Movie>> {
        return movieUseCase.searchFavoriteMovie(query).asLiveData()
    }
}