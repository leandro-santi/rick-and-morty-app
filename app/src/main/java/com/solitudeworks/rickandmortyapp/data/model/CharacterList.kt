package com.solitudeworks.rickandmortyapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CharacterList(
    val info: Info?,
    val results: List<CharacterDetail>?,
)
