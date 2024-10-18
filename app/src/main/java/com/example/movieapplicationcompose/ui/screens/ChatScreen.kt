package com.example.movieapplicationcompose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.movieapplicationcompose.data.models.Message
import com.example.movieapplicationcompose.viewModel.ChatViewModel

@Composable
fun ChatScreen(navController: NavHostController) {
    val viewModel: ChatViewModel = hiltViewModel()
    val chatResponse by viewModel.chatResponse.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val messages by viewModel.messages.collectAsState()

    val messageInput = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1F),
            reverseLayout = true
        ) {
            items(messages) { message ->
                MessageItem(message)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .padding(bottom = 100.dp)
        ) {
            TextField(
                value = messageInput.value,
                onValueChange = { messageInput.value = it },
                modifier = Modifier.weight(1F),
                placeholder = { Text("Mesajınızı yazın...") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (messageInput.value.isNotEmpty()) {
                    viewModel.sendMessage(messageInput.value)
                    messageInput.value = ""
                }
            }) {
                Text("Send")
            }
        }

        if (loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}

@Composable
fun MessageItem(message: Message) {
    val backgroundColor = if (message.role == "user") Color(0xFFD3D3D3) else Color.White

    Text(
        text = message.content,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(backgroundColor, Color.Transparent)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        color = Color.Black
    )
}


