package com.example.movieapplicationcompose.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapplicationcompose.data.models.ChatRequest
import com.example.movieapplicationcompose.data.models.Message
import com.example.movieapplicationcompose.data.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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
                val request = ChatRequest(
                    model = "gpt-3.5-turbo",
                    messages = listOf(Message("user", prompt)),
                    max_tokens = 150
                )
                val response = chatRepository.getChatCompletion(request)
                _chatResponse.value = response.choices.firstOrNull()?.text ?: "No response"

                _messages.value += Message("user", prompt)
                _messages.value += Message("assistant", _chatResponse.value)
            } catch (e: Exception) {
                _chatResponse.value = "Error: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}