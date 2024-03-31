package com.solitudeworks.rickandmortyapp.utils

sealed class LoadingState {

    data object Ready : LoadingState()

    data object Loading : LoadingState()

    data class Error(val exception: Exception) : LoadingState()

}
