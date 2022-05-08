package space.iqbalsyafiq.movieapp.core.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import space.iqbalsyafiq.movieapp.core.data.source.local.LocalDataSource
import space.iqbalsyafiq.movieapp.core.data.source.remote.RemoteDataSource
import space.iqbalsyafiq.movieapp.core.data.source.remote.network.ApiResponse
import space.iqbalsyafiq.movieapp.core.data.source.remote.response.MovieResponse
import space.iqbalsyafiq.movieapp.core.domain.model.Movie
import space.iqbalsyafiq.movieapp.core.domain.repository.IMovieRepository
import space.iqbalsyafiq.movieapp.core.utils.AppExecutors
import space.iqbalsyafiq.movieapp.core.utils.DataMapper

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
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

    override fun setFavoriteMovie(tourism: Movie, state: Boolean) {
        val tourismEntity = DataMapper.mapDomainToEntity(tourism)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(tourismEntity, state) }
    }
}