package com.example.movieapplicationcompose.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movieapplicationcompose.data.database.entities.FavoriteMovie

@Dao
interface FavoriteMovieDao {

    @Insert
    suspend fun insert(favoriteMovie: FavoriteMovie)

    @Query("SELECT * FROM favorite_movies")
    suspend fun getAllFavorites(): List<FavoriteMovie>

    @Query("DELETE FROM favorite_movies WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_movies WHERE id = :id)")
    suspend fun isFavorite(id: Int): Boolean
}