package com.example.movieapplicationcompose.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapplicationcompose.data.models.ChatRequest
import com.example.movieapplicationcompose.data.models.Message
import com.example.movieapplicationcompose.data.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _chatResponse = MutableStateFlow("")
    val chatResponse: StateFlow<String> get() = _chatResponse

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> get() = _messages


    fun sendMessage(prompt: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                // Kullanıcının mesajını ekle
                val userMessage = Message("user", prompt)
                _messages.value += userMessage

                // Tüm mesajları kullanarak istek oluştur
                val request = ChatRequest(
                    model = "gpt-3.5-turbo",
                    messages = _messages.value,
                    max_tokens = 150
                )

                // API'den cevap al
                val response = chatRepository.getChatCompletion(request)

                // Yanıtı logla
                Log.d("ChatViewModel", "API Response: $response")

                // Asistanın mesajını al
                if (response != null && response.choices.isNotEmpty()) {
                    val assistantMessage = response.choices.first().message.content
                    _messages.value += Message("assistant", assistantMessage)
                } else {
                    Log.d("ChatViewModel", "Received null or empty response")
                    _messages.value += Message("assistant", "No response")
                }

            } catch (e: Exception) {
                Log.e("ChatViewModel", "Error: ${e.message}")
                if (e is HttpException) {
                    Log.e("ChatViewModel", "HTTP Error: ${e.code()} - ${e.message()}")
                }
                _messages.value += Message("assistant", "Error: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }

//    fun sendMessage(prompt: String) {
//        viewModelScope.launch {
//            _loading.value = true
//            try {
//                val userMessage = Message("user", prompt)
//                _messages.value += userMessage
//
//                val request = ChatRequest(
//                    model = "gpt-3.5-turbo",
//                    messages = _messages.value,
//                    max_tokens = 150
//                )
//
//                delay(2000)
//
//                val response = chatRepository.getChatCompletion(request)
//
//                val assistantMessage = response.choices.firstOrNull()?.message?.content ?: "No response"
//
//                _messages.value += Message("assistant", assistantMessage)
//
//            } catch (e: Exception) {
//                _messages.value += Message("assistant", "Error: ${e.message}")
//            } finally {
//                _loading.value = false
//            }
//        }
//    }
}