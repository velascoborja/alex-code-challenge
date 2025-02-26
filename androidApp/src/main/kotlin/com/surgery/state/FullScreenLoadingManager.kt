package com.surgery.state

import androidx.compose.runtime.mutableStateOf

object FullScreenLoadingManager {
    val isLoading = mutableStateOf(false)

    val error = mutableStateOf<String?>(null)

    fun showLoader() {
        isLoading.value = true
    }

    fun hideLoader() {
        isLoading.value = false
    }

    fun showError(message: String) {
        error.value = message
    }

    fun hideError() {
        error.value = null
    }
}