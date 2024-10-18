package com.example.movieapplicationcompose.data.models

data class ChatRequest(
    val model: String,
    val messages: List<Message>,
    val max_tokens: Int
)

data class Message(
    val role: String,
    val content: String
)
