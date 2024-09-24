package com.example.dictionary_app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary_app.domain.model.WordItem
import com.example.dictionary_app.domain.repository.DictionaryRepository
import com.example.dictionary_app.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dictionaryRepository: DictionaryRepository
): ViewModel() {

    private val _mainState = MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()



    private var searchJob: Job? = null

    init { // When we open a new app, the word we start with or intialized with will be 'word'
        _mainState.update { it.copy(searchWord = "Word") }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            loadWordResults()

        }
    }

    fun onEvent(mainUiEvents: MainUiEvents) {
        when (mainUiEvents) {
            MainUiEvents.OnSearchClick -> {
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    loadWordResults()

                }
            }
            is MainUiEvents.OnSeacrchWordChange -> {
                _mainState.update {
                    it.copy(searchWord = mainUiEvents.newWord.lowercase())
                }
            }
        }
    }

    private fun loadWordResults(){
        viewModelScope.launch {
            dictionaryRepository.getWordResult(
                mainState.value.searchWord
            ).collect { result ->
                when (result) {
                    is Result.Error -> Unit
                    is Result.Loading -> {
                        _mainState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                    is Result.Success -> {
                        result.data?.let { wordItem -> _mainState.update { it.copy(wordItem = wordItem) }  }
                    }
                }
            }
        }
    }

}