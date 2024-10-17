package com.example.movieapplicationcompose.data.repository

import com.example.movieapplicationcompose.data.database.MovieDatabase
import com.example.movieapplicationcompose.data.database.entities.FavoriteMovie
import com.example.movieapplicationcompose.data.models.Data
import com.example.movieapplicationcompose.data.models.Details
import com.example.movieapplicationcompose.domain.ApiInterface
import javax.inject.Inject
import com.example.movieapplicationcompose.utils.Result
import retrofit2.Response

class MovieRepository @Inject constructor(
    private val database: MovieDatabase,
    private val api: ApiInterface
) {

    private inline fun <T> handleApiCall(call: () -> Response<T>): Result<T> {
        return try {
            val response = call()
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!)
            } else {
                Result.Error(Exception("API Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getMovieList(page: Int): Result<List<Data>> {
        return when (val result = handleApiCall { api.getMovies(page) }) {
            is Result.Success -> { Result.Success(result.data.data) }
            is Result.Error -> { Result.Error(result.exception) }
        }
    }

    suspend fun getDetailById(id: Int): Result<Details> {
        return handleApiCall { api.getDetailsById(id) }
    }

    suspend fun addFavoriteMovie(movie: FavoriteMovie) {
        if (!isMovieFavorite(movie.id)) {
            database.favoriteMovieDao().insert(movie)
        }
    }

    suspend fun removeFavoriteMovie(id: Int) {
        database.favoriteMovieDao().deleteById(id)
    }

    suspend fun getAllFavoriteMovies(): List<FavoriteMovie> {
        return database.favoriteMovieDao().getAllFavorites()
    }

    private suspend fun isMovieFavorite(id: Int): Boolean {
        return database.favoriteMovieDao().isFavorite(id)
    }
}