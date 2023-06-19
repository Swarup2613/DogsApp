package com.example.dogsapp.utils

sealed class Response<T>(
    val data: T? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean? = null
) {

    class Loading<T>(isLoading: Boolean?) : Response<T>(isLoading = isLoading)
    class Success<T>(data: T? = null) : Response<T>(data = data)
    class Error<T>(errorMessage: String?) : Response<T>(errorMessage = errorMessage)
}