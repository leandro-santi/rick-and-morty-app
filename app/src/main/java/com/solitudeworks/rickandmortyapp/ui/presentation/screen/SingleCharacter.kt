package com.solitudeworks.rickandmortyapp.ui.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.solitudeworks.rickandmortyapp.R
import com.solitudeworks.rickandmortyapp.ui.presentation.shared.errorItem
import com.solitudeworks.rickandmortyapp.ui.presentation.shared.loadingView
import com.solitudeworks.rickandmortyapp.ui.presentation.viewmodel.SingleCharacterViewModel
import com.solitudeworks.rickandmortyapp.utils.LoadingState

@Composable
fun singleCharacter(characterId: Int) {
    val singleCharacterViewModel: SingleCharacterViewModel =
        hiltViewModel<SingleCharacterViewModel>()
    if (singleCharacterViewModel.characterId == 0) {
        singleCharacterViewModel.characterId = characterId
        singleCharacterViewModel.getSingleCharacter()
    }
    val character by singleCharacterViewModel.singleCharacter.collectAsState()

    Column(
        modifier =
            Modifier
                .padding(all = 8.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        // Character's image
        AsyncImage(
            model =
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(character.image)
                    .crossfade(true)
                    .crossfade(1000)
                    .build(),
            contentDescription = null,
            modifier =
                Modifier
                    .fillMaxSize(),
            error = painterResource(id = R.drawable.no_internet),
            contentScale = ContentScale.FillWidth,
        )

        // Character's name
        Text(
            text = stringResource(R.string.text_character),
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp),
        )
        Text(text = "${character.name}")

        // Character's status
        Text(
            text = stringResource(id = R.string.text_status),
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp),
        )

        Row(
            modifier =
                Modifier
                    .padding(start = 16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter =
                    painterResource(
                        id =
                            when (character.status) {
                                "Alive" -> R.drawable.ic_dot_green
                                "Dead" -> R.drawable.ic_dot_red
                                "unknown" -> R.drawable.ic_dot_grey
                                else -> R.drawable.ic_dot_grey
                            },
                    ),
                contentDescription = null,
                tint = Color.Unspecified,
            )

            Text(text = "  ${character.status ?: ""} ")
        }

        // Character's specie and gender
        Text(
            text = stringResource(id = R.string.text_species_and_gender),
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp),
        )
        Text(text = "${character.species} / ${character.gender}")

        // Character's location
        Text(
            text = stringResource(R.string.text_last_known_location),
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp),
        )
        Text(text = character.location?.name ?: "")
    }

    val loadingState by singleCharacterViewModel.loadingState.collectAsState()

    when (loadingState) {
        is LoadingState.Loading -> {
            loadingView(modifier = Modifier.fillMaxSize())
        }

        is LoadingState.Ready -> {
        }

        is LoadingState.Error -> {
            errorItem(
                (loadingState as LoadingState.Error).exception.localizedMessage ?: "Unknown error",
                onClickRetry = { singleCharacterViewModel.getSingleCharacter() },
            )
        }
    }
}
