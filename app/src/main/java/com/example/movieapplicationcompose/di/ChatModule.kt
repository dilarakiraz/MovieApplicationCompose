package com.example.movieapplicationcompose.di

import com.example.movieapplicationcompose.data.repository.ChatRepository
import com.example.movieapplicationcompose.utils.Util.api_key
import com.google.ai.client.generativeai.GenerativeModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object ChatModule {

    @Provides
    @Named("textGenerativeModel")
    fun provideGenerativeModel(): GenerativeModel {
        return GenerativeModel(modelName = "gemini-pro", apiKey = api_key)
    }

    @Provides
    @Named("imageGenerativeModel")
    fun provideImageGenerativeModel(): GenerativeModel {
        return GenerativeModel(modelName = "gemini-1.5-flash", apiKey = api_key)
    }

    @Provides
    fun provideChatRepository(
        @Named("textGenerativeModel") generativeModel: GenerativeModel,
        @Named("imageGenerativeModel") imageGenerativeModel: GenerativeModel
    ): ChatRepository {
        return ChatRepository(generativeModel, imageGenerativeModel)
    }
}