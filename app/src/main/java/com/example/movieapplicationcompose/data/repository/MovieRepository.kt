package com.example.movieapplicationcompose.data.repository

import com.example.movieapplicationcompose.data.database.MovieDatabase
import com.example.movieapplicationcompose.data.database.entities.FavoriteMovie
import com.example.movieapplicationcompose.data.models.Data
import com.example.movieapplicationcompose.data.models.Details
import com.example.movieapplicationcompose.domain.ApiInterface
import javax.inject.Inject
import com.example.movieapplicationcompose.utils.Result

class MovieRepository @Inject constructor(
    private val database: MovieDatabase,
    private val api: ApiInterface
) {

    suspend fun getMovieList(page: Int): Result<List<Data>> {
        return try {
            val response = api.getMovies(page)
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!.data)
            } else {
                Result.Error(Exception("API Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getDetailById(id: Int): Result<Details> {
        return try {
            val response = api.getDetailsById(id)
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!)
            } else {
                Result.Error(Exception("API Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun addFavoriteMovie(movie: FavoriteMovie) {
        val isFavorite = database.favoriteMovieDao().isFavorite(movie.id)
        if (!isFavorite) {
            database.favoriteMovieDao().insert(movie)
        } else {
        }
    }

    suspend fun removeFavoriteMovie(id: Int) {
        database.favoriteMovieDao().deleteById(id)
    }

    suspend fun getAllFavoriteMovies(): List<FavoriteMovie> {
        return database.favoriteMovieDao().getAllFavorites()
    }
}