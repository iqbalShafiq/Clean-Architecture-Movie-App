package space.iqbalsyafiq.movieapp.core.data.source.local

import kotlinx.coroutines.flow.Flow
import space.iqbalsyafiq.movieapp.core.data.source.local.entity.MovieEntity
import space.iqbalsyafiq.movieapp.core.data.source.local.room.MovieDao

class LocalDataSource(private val movieDao: MovieDao) {
    fun getAllMovie(): Flow<List<MovieEntity>> = movieDao.getAllMovie()
    fun getFavoriteMovie(): Flow<List<MovieEntity>> = movieDao.getFavoriteMovie()
    suspend fun insertMovie(movies: List<MovieEntity>) = movieDao.insertMovie(movies)
    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        movieDao.updateFavoriteMovie(movie)
    }
}