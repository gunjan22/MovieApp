package com.example.myapplication.data.remote

import com.example.myapplication.ConstValue
import com.example.myapplication.data.model.MovieDetail
import com.example.myapplication.data.model.MovieResultModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String = ConstValue.API_KEY,
        @Query("page") page: Int
    ): MovieResultModel

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String = ConstValue.API_KEY
    ): MovieDetail


    @GET("search/movie")
    suspend fun getMovieSearch(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = ConstValue.API_KEY,
        @Query("page") page: Int
    ): MovieResultModel

}