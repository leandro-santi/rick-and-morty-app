package com.solitudeworks.rickandmortyapp.utils

import androidx.annotation.StringRes
import com.solitudeworks.rickandmortyapp.R

enum class RickAndMortyScreenTitles (@StringRes val title:Int) {
    CharacterList(title = R.string.text_character_list),
    SearchList(title = R.string.text_search_list),
    SingleCharacter(title = R.string.text_character)
}