package com.example.movieapplicationcompose.di

import android.app.Application
import androidx.room.Room
import com.example.movieapplicationcompose.data.database.FavoriteMovieDao
import com.example.movieapplicationcompose.data.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): MovieDatabase {
        return Room.databaseBuilder(app, MovieDatabase::class.java, "movie_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideMovieDao(db: MovieDatabase): FavoriteMovieDao{
        return db.favoriteMovieDao()
    }
}