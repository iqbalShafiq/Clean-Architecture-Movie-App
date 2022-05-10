package space.iqbalsyafiq.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import space.iqbalsyafiq.core.data.Resource
import space.iqbalsyafiq.core.domain.model.Movie
import space.iqbalsyafiq.core.domain.repository.IMovieRepository

class MovieInteractor(private val movieRepository: IMovieRepository) : MovieUseCase {
    override fun getAllMovie(): Flow<Resource<List<Movie>>> = movieRepository.getAllMovie()

    override fun getFavoriteMovie(): Flow<List<Movie>> = movieRepository.getFavoriteMovie()

    override fun searchFavoriteMovie(query: String): Flow<List<Movie>> =
        movieRepository.searchFavoriteMovie(query)

    override fun setFavoriteMovie(tourism: Movie, state: Boolean) =
        movieRepository.setFavoriteMovie(tourism, state)
}