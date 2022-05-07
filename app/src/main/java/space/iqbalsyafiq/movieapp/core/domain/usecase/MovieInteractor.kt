package space.iqbalsyafiq.movieapp.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import space.iqbalsyafiq.movieapp.core.data.source.Resource
import space.iqbalsyafiq.movieapp.core.domain.model.Movie
import space.iqbalsyafiq.movieapp.core.domain.repository.IMovieRepository

class MovieInteractor(private val movieRepository: IMovieRepository) : MovieUseCase {
    override fun getAllMovie(): Flow<Resource<List<Movie>>> = movieRepository.getAllMovie()

    override fun getFavoriteMovie(): Flow<List<Movie>> = movieRepository.getFavoriteMovie()

    override fun setFavoriteMovie(tourism: Movie, state: Boolean) =
        movieRepository.setFavoriteMovie(tourism, state)
}