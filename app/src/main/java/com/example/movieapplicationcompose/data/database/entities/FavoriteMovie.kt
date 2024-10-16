package com.example.movieapplicationcompose.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoriteMovie (
    @PrimaryKey val id: Int,
    val title: String,
    val poster: String,
    val plot: String,
    val imdbRating: String
)