package com.example.dictionary_app.data.dto

data class WordItemDto(
    val meanings: List<MeaningDto>? = null,
    val word: String? = null,
    val phonetic: String? = null
)