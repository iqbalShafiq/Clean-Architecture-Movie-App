package space.iqbalsyafiq.core.data.source.local

import kotlinx.coroutines.flow.Flow
import space.iqbalsyafiq.core.data.source.local.entity.MovieEntity
import space.iqbalsyafiq.core.data.source.local.room.MovieDao

class LocalDataSource(private val movieDao: MovieDao) {
    fun getAllMovie(): Flow<List<MovieEntity>> = movieDao.getAllMovie()

    fun getFavoriteMovie(): Flow<List<MovieEntity>> = movieDao.getFavoriteMovie()

    fun searchFavoriteMovie(query: String): Flow<List<MovieEntity>> =
        movieDao.searchFavoriteMovie(query)

    suspend fun insertMovie(movies: List<MovieEntity>) = movieDao.insertMovie(movies)

    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        movieDao.updateFavoriteMovie(movie)
    }
}