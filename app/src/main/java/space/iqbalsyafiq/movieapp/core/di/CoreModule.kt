package space.iqbalsyafiq.movieapp.core.di

import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import space.iqbalsyafiq.movieapp.core.data.MovieRepository
import space.iqbalsyafiq.movieapp.core.data.source.local.LocalDataSource
import space.iqbalsyafiq.movieapp.core.data.source.local.room.MovieDatabase
import space.iqbalsyafiq.movieapp.core.data.source.remote.RemoteDataSource
import space.iqbalsyafiq.movieapp.core.data.source.remote.network.ApiService
import space.iqbalsyafiq.movieapp.core.domain.repository.IMovieRepository
import space.iqbalsyafiq.movieapp.core.utils.AppExecutors
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val databaseModule = module {
    factory {
        get<MovieDatabase>().movieDao()
    }
    single {
        Room.databaseBuilder(androidContext(), MovieDatabase::class.java, "Movie.db")
            .fallbackToDestructiveMigration().build()
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IMovieRepository> { MovieRepository(get(), get(), get()) }
}