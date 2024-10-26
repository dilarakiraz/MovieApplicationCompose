package com.example.movieapplicationcompose.ui.chat

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil.size.Size
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun ChatScreen(
    navController: NavHostController,
) {
    val chatViewModel: ChatViewModel = hiltViewModel()
    val chatState = chatViewModel.chatState.collectAsState().value

    val uriState = remember { MutableStateFlow("") }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let { uriState.value = it.toString() }
        }
    )

    val bitmap = getBitmap(uriState.collectAsState().value)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            reverseLayout = true
        ) {
            items(chatState.chatList) { chat ->
                if (chat.isFromUser) {
                    UserChatItem(prompt = chat.prompt, bitmap = chat.bitmap)
                } else {
                    ModelChatItem(response = chat.prompt)
                }
            }
        }

        InputRow(chatState, chatViewModel, bitmap, uriState, imagePicker)

        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun InputRow(
    chatState: ChatState,
    viewModel: ChatViewModel,
    bitmap: Bitmap?,
    uriState: MutableStateFlow<String>,
    imagePicker: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, start = 4.dp, end = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            bitmap?.let {
                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(bottom = 2.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    contentDescription = "picked image",
                    contentScale = ContentScale.Crop,
                    bitmap = it.asImageBitmap()
                )
            }

            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        imagePicker.launch(
                            PickVisualMediaRequest.Builder()
                                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                .build()
                        )
                    },
                imageVector = Icons.Rounded.AddCircle,
                contentDescription = "Add Photo",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        TextField(
            modifier = Modifier.weight(1f),
            value = chatState.prompt,
            onValueChange = {
                viewModel.onEvent(ChatUiEvent.UpdatePrompt(it))
            },
            placeholder = {
                Text(text = "Type a prompt")
            }
        )

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    viewModel.onEvent(ChatUiEvent.SendPrompt(chatState.prompt, bitmap))
                    uriState.value = ""
                },
            imageVector = Icons.Rounded.Send,
            contentDescription = "Send prompt",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun UserChatItem(prompt: String, bitmap: Bitmap?) {
    Column(
        modifier = Modifier.padding(start = 100.dp, bottom = 16.dp)
    ) {
        bitmap?.let {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .padding(bottom = 2.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                bitmap = it.asImageBitmap()
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp),
            text = prompt,
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun ModelChatItem(response: String) {
    Column(
        modifier = Modifier.padding(end = 100.dp, bottom = 16.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp),
            text = response,
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun getBitmap(uri: String): Bitmap? {
    val imageState: AsyncImagePainter.State = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(uri)
            .size(Size.ORIGINAL)
            .build()
    ).state

    return if (imageState is AsyncImagePainter.State.Success) {
        imageState.result.drawable.toBitmap()
    } else {
        null
    }
}