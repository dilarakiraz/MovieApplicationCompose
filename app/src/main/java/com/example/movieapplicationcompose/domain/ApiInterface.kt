package com.example.movieapplicationcompose.domain

import com.example.movieapplicationcompose.data.models.Details
import com.example.movieapplicationcompose.data.models.MoviesList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("movies?")
    suspend fun getMovies(
        @Query("page") page: Int
    ):Response<MoviesList>

    @GET("movies/{movie_id}")
    suspend fun getDetailsById(
        @Path("movie_id") id: Int
    ): Response<Details>
}