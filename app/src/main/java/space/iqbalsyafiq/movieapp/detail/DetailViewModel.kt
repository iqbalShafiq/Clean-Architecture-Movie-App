package space.iqbalsyafiq.movieapp.detail

import androidx.lifecycle.ViewModel
import space.iqbalsyafiq.movieapp.core.domain.model.Movie
import space.iqbalsyafiq.movieapp.core.domain.usecase.MovieUseCase

class DetailViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    fun setFavoriteTourism(movie: Movie, newStatus: Boolean) = movieUseCase.setFavoriteMovie(
        movie, newStatus
    )
}