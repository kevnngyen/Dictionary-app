package com.example.dictionary_app.presentation

/*
class that work with every event (Changing the word, searchign for it, etc..)
 */

sealed class MainUiEvents {
    data class OnSeacrchWordChange(val newWord: String): MainUiEvents()
    object OnSearchClick : MainUiEvents()
}