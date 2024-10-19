package com.example.movieapplicationcompose.data.models

data class ChatResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: MessageContent
)

data class MessageContent(
    val role: String,
    val content: String
)