package com.example.ahyaha.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ahyaha.model.News
import com.example.ahyaha.repository.NewsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class NewsUIState(
    val isLoading: Boolean = false,
    val newsList: List<News> = emptyList(),
    val error: String? = null
)

class NewsViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(NewsUIState())
    val uiState: StateFlow<NewsUIState> = _uiState.asStateFlow()

    init {
        getNews()
    }

    fun getNews() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                delay(1000) // Simulate network delay
                val news = NewsRepository.getAllBloodTypes()
                _uiState.value = _uiState.value.copy(newsList = news, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message, isLoading = false)
            }
        }
    }
    
    fun refreshNews() {
        getNews()
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
