package com.example.audioswitch_equalizedevo.ui.viewModels

import androidx.lifecycle.ViewModel
import com.example.audioswitch_equalizedevo.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

//taken viewModel inspiration from my scramble codelab project
class viewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()
    
}