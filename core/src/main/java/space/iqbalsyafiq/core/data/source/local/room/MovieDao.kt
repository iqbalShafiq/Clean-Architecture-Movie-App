package space.iqbalsyafiq.core.data.source.local.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import space.iqbalsyafiq.core.data.source.local.entity.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAllMovie(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE is_favorite = 1")
    fun getFavoriteMovie(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE is_favorite = 1 AND title LIKE :query")
    fun searchFavoriteMovie(
        query: String
    ): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: List<MovieEntity>)

    @Update
    fun updateFavoriteMovie(movie: MovieEntity)
}