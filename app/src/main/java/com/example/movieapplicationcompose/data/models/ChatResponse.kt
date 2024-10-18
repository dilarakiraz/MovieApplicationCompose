package com.example.movieapplicationcompose.data.models

data class ChatResponse(
    val choices: List<Choice>
)

data class Choice(
    val text: String
)
