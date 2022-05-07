package space.iqbalsyafiq.movieapp.core.data.source.remote.network

import retrofit2.http.GET
import space.iqbalsyafiq.movieapp.core.data.source.remote.response.ListMovieResponse

interface ApiService {
    @GET("/movie/now_playing")
    suspend fun getList(): ListMovieResponse
}