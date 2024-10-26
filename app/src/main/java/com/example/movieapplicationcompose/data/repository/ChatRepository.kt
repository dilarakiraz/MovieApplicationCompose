package com.example.movieapplicationcompose.data.repository

import android.graphics.Bitmap
import com.example.movieapplicationcompose.data.models.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val generativeModel: GenerativeModel,
    private val imageGenerativeModel: GenerativeModel
) {

    suspend fun getResponse(prompt: String): Chat {
        return try {
            val response = withContext(Dispatchers.IO) {
                generativeModel.generateContent(prompt)
            }
            Chat(prompt = response.text ?: "error", bitmap = null, isFromUser = false)
        } catch (e: Exception) {
            Chat(prompt = e.message ?: "error", bitmap = null, isFromUser = false)
        }
    }

    suspend fun getResponseWithImage(prompt: String, bitmap: Bitmap): Chat {
        return try {
            val inputContent = content {
                image(bitmap)
                text(prompt)
            }
            val response = withContext(Dispatchers.IO) {
                imageGenerativeModel.generateContent(inputContent)
            }
            Chat(prompt = response.text ?: "error", bitmap = null, isFromUser = false)
        } catch (e: Exception) {
            Chat(prompt = e.message ?: "error", bitmap = null, isFromUser = false)
        }
    }
}