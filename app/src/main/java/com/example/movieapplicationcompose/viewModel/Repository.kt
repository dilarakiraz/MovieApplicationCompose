package com.example.movieapplicationcompose.viewModel

import com.example.movieapplicationcompose.models.MoviesList
import com.example.movieapplicationcompose.utils.RetrofitInstance
import retrofit2.Response

class Repository {
    suspend fun getMovieList(page: Int): Response<MoviesList> {
        return RetrofitInstance.api.getMovies(page)
    }
}