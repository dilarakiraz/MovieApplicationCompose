package com.example.movieapplicationcompose.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapplicationcompose.data.database.entities.FavoriteMovie

@Database(entities = [FavoriteMovie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}