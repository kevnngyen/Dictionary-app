package com.example.dictionary_app.data.api

import com.example.dictionary_app.data.dto.WordResultsDto
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * @author kevnngyen
 */
interface DictionaryApi {

    @GET("{word}")
    suspend fun getWordResults(@Path("word") word: String) : WordResultsDto // passes a word into the api <word>

    companion object {
        const val BASE_URL = "https://api.dictionaryapi.dev/api/v2/entries/en/"
    }

}