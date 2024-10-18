package com.example.movieapplicationcompose.data.repository

import com.example.movieapplicationcompose.data.models.ChatRequest
import com.example.movieapplicationcompose.data.models.ChatResponse
import com.example.movieapplicationcompose.domain.OpenAiService
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val openAiService: OpenAiService
) {
    suspend fun getChatCompletion(request: ChatRequest): ChatResponse {
        return openAiService.getChatCompletion(request)
    }
}