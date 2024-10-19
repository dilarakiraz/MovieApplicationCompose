package com.example.movieapplicationcompose.domain

import com.example.movieapplicationcompose.data.models.ChatRequest
import com.example.movieapplicationcompose.data.models.ChatResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAiService {
    @Headers("Content-Type: application/json")
    @POST("v1/chat/completions")
    suspend fun getChatCompletion(
        @Body request: ChatRequest
    ): ChatResponse
}