package space.iqbalsyafiq.core.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import space.iqbalsyafiq.core.data.source.local.LocalDataSource
import space.iqbalsyafiq.core.data.source.remote.RemoteDataSource
import space.iqbalsyafiq.core.data.source.remote.network.ApiResponse
import space.iqbalsyafiq.core.data.source.remote.response.MovieResponse
import space.iqbalsyafiq.core.domain.model.Movie
import space.iqbalsyafiq.core.domain.repository.IMovieRepository
import space.iqbalsyafiq.core.utils.DataMapper

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IMovieRepository {
    override fun getAllMovie(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovie().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean = data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllMovie()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val tourismList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertMovie(tourismList)
            }
        }.asFlow()

    override fun getFavoriteMovie(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovie().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun searchFavoriteMovie(query: String): Flow<List<Movie>> {
        return localDataSource.searchFavoriteMovie(query).map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteMovie(tourism: Movie, state: Boolean) {
        val tourismEntity = DataMapper.mapDomainToEntity(tourism)
        runBlocking {
            withContext(Dispatchers.IO) {
                localDataSource.setFavoriteMovie(tourismEntity, state)
            }
        }
    }
}

