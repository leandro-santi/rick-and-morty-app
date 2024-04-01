package com.solitudeworks.rickandmortyapp.ui.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.solitudeworks.rickandmortyapp.data.domain.GetSingleCharactersUseCase
import com.solitudeworks.rickandmortyapp.data.model.CharacterDetail
import com.solitudeworks.rickandmortyapp.utils.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleCharacterViewModel
    @Inject
    constructor(
        private val getSingleCharactersUseCase: GetSingleCharactersUseCase,
    ) : ViewModel() {
        private val _loadingState = MutableStateFlow<LoadingState>(LoadingState.Loading)
        val loadingState = _loadingState.asStateFlow()

        private val _singleCharacter =
            MutableStateFlow(
                CharacterDetail(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                ),
            )
        var singleCharacter: StateFlow<CharacterDetail> = _singleCharacter.asStateFlow()

        var characterId: Int = 0

        private val dBScope = CoroutineScope(Dispatchers.IO)

        fun getSingleCharacter() {
            _loadingState.value = LoadingState.Loading
            dBScope.launch {
                try {
                    _singleCharacter.value = getSingleCharactersUseCase.getSingleCharacterUseCase(characterId)
                    _loadingState.value = LoadingState.Ready
                } catch (e: Exception) {
                    _loadingState.value = LoadingState.Error(e)
                }
            }
        }
    }
