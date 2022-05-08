package space.iqbalsyafiq.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import space.iqbalsyafiq.core.data.Resource
import space.iqbalsyafiq.core.domain.model.Movie

interface MovieUseCase {
    fun getAllMovie(): Flow<Resource<List<Movie>>>
    fun getFavoriteMovie(): Flow<List<Movie>>
    fun setFavoriteMovie(tourism: Movie, state: Boolean)
}