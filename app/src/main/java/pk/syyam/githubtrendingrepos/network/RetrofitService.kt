package pk.syyam.githubtrendingrepos.network

import pk.syyam.githubtrendingrepos.model.TrendingReposResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

    @GET("search/repositories?q=language=+sort:stars")
    suspend fun getTrendingRepos(
        @Query("page") page: Int
    ): TrendingReposResponse

}