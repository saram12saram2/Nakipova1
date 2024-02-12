package com.example.ainkino

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @Headers("x-api-key: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("/api/v2.2/films/top")
    fun getPopularMovies(
        @Query("type") type: String
    ): Call<MovieResponse>

    @Headers("x-api-key: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("/api/v2.2/films/{id}")
    fun getMovieDetail(@Path("id") id: Int): Call<MovieDetail>


    companion object {
        fun create(): MovieService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://kinopoiskapiunofficial.tech")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(MovieService::class.java)
        }
    }
}
