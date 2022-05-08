package space.iqbalsyafiq.core.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import space.iqbalsyafiq.core.data.source.remote.RemoteDataSource
import space.iqbalsyafiq.core.data.source.remote.network.ApiResponse
import space.iqbalsyafiq.core.data.source.remote.response.MovieResponse
import space.iqbalsyafiq.core.domain.model.Movie
import space.iqbalsyafiq.core.domain.repository.IMovieRepository
import space.iqbalsyafiq.core.utils.AppExecutors
import space.iqbalsyafiq.core.utils.DataMapper

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: space.iqbalsyafiq.core.data.source.local.LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {
    override fun getAllMovie(): Flow<space.iqbalsyafiq.core.data.Resource<List<Movie>>> =
        object :
            space.iqbalsyafiq.core.data.NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
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

    override fun setFavoriteMovie(tourism: Movie, state: Boolean) {
        val tourismEntity = DataMapper.mapDomainToEntity(tourism)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(tourismEntity, state) }
    }
}