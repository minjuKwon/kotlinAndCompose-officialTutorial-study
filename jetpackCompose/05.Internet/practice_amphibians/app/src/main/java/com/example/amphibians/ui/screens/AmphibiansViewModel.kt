package com.example.amphibians.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibians.AmphibiansApplication
import com.example.amphibians.data.AmphibiansRepository
import com.example.amphibians.network.Amphibians
import kotlinx.coroutines.launch
import java.io.IOException

class AmphibiansViewModel(
    private val amphibiansRepository: AmphibiansRepository
): ViewModel() {

    var amphibiansUiState: AmphibiansUiState by mutableStateOf(AmphibiansUiState.Loading)
        private set

    init {
        getInformation()
    }

    fun getInformation() {
        viewModelScope.launch {
            amphibiansUiState = AmphibiansUiState.Loading
            amphibiansUiState = try{
                AmphibiansUiState.Success(amphibiansRepository.getAmphibiansInformation())
            }catch (e: IOException){
                AmphibiansUiState.Error
            }
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory=viewModelFactory{
            initializer {
                val application=(this[APPLICATION_KEY] as AmphibiansApplication)
                val amphibiansRepository=application.container.amphibiansRepository
                AmphibiansViewModel(amphibiansRepository=amphibiansRepository)
            }
        }
    }

    sealed interface AmphibiansUiState{
        data class Success(val list: List<Amphibians>): AmphibiansUiState
        object Error: AmphibiansUiState
        object Loading: AmphibiansUiState
    }
}