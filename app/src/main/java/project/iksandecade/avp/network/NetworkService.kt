package project.iksandecade.avp.network

import project.iksandecade.avp.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface NetworkService {
    @GET("/v2/top-headlines")
    fun getTopHeadlines(
            @Query("apiKey") apiKey: String?,
            @Query("country") country: String?,
            @Query("pageSize") pageSize: Int?,
            @Query("page") page: Int?
    ): Observable<NewsResponse>
}