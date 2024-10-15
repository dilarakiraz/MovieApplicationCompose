package com.example.movieapplicationcompose.domain

import com.example.movieapplicationcompose.models.MoviesList
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("movies?/page=1")
    suspend fun getMovies(

    ):Response<MoviesList>
}