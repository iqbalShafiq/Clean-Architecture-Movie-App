package space.iqbalsyafiq.core.data.source.remote.network

import retrofit2.http.GET
import retrofit2.http.Query
import space.iqbalsyafiq.core.data.source.remote.response.ListMovieResponse

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getList(
        @Query("api_key") apiToken: String = "ea4695c99ce20bae9e18aec65dfe1498"
    ): ListMovieResponse
}