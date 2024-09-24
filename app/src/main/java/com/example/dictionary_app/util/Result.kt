package com.example.dictionary_app.util

sealed class Result<T>(
    val data: T? = null,
    val message: String? = null
) {

    // Should display the data once loaded
    class Success<T>(data: T?) : Result<T>(data)

    // When something goes wrong then we get an Error
    class Error<T>(message: String) : Result<T>(null, message)

    // loading information (loading icon/animation)
    class Loading<T>(val isLoading: Boolean = true) : Result<T>(null)
}