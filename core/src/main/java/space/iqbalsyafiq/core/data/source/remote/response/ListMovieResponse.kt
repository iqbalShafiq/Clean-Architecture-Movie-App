package space.iqbalsyafiq.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListMovieResponse(
    val page: Int?,
    val results: List<MovieResponse>,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)