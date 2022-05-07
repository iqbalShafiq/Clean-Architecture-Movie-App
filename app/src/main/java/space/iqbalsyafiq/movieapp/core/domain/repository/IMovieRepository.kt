package space.iqbalsyafiq.movieapp.core.domain.repository

import kotlinx.coroutines.flow.Flow
import space.iqbalsyafiq.movieapp.core.data.source.Resource
import space.iqbalsyafiq.movieapp.core.domain.model.Movie

interface IMovieRepository {
    fun getAllMovie(): Flow<Resource<List<Movie>>>

    fun getFavoriteMovie(): Flow<List<Movie>>

    fun setFavoriteMovie(tourism: Movie, state: Boolean)
}