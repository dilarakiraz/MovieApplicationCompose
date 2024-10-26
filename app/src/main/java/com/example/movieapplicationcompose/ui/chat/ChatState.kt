package com.example.movieapplicationcompose.ui.chat

import android.graphics.Bitmap
import com.example.movieapplicationcompose.data.models.Chat

data class ChatState (
    val chatList: MutableList<Chat> = mutableListOf(),
    val prompt: String = "",
    val bitmap: Bitmap? = null
)
