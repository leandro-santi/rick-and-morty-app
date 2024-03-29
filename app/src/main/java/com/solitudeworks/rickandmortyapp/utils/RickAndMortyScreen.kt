package com.solitudeworks.rickandmortyapp.utils

import androidx.annotation.StringRes
import com.solitudeworks.rickandmortyapp.R

enum class RickAndMortyScreen (@StringRes val title:Int) {
    CharacterList(title = R.string.text_character_list),
    LocationList(title = R.string.text_location_list),
    SingleCharacter(title = R.string.text_character)
}