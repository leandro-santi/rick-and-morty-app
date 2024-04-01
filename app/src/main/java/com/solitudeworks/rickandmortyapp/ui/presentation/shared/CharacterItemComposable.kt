package com.solitudeworks.rickandmortyapp.ui.presentation.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.solitudeworks.rickandmortyapp.R
import com.solitudeworks.rickandmortyapp.data.model.CharacterDetail
import com.solitudeworks.rickandmortyapp.utils.RickAndMortyScreenTitles

@Composable
fun characterItem(
    character: CharacterDetail,
    navController: NavHostController,
) {
    Row(
        modifier =
            Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()
                .clickable {
                    navController.navigate(RickAndMortyScreenTitles.SingleCharacter.name + "/${character.id}/${character.name}")
                },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top,
    ) {
        // Character's image
        AsyncImage(
            model =
                ImageRequest.Builder(LocalContext.current)
                    .data(character.image)
                    .crossfade(true)
                    .crossfade(1000)
                    .build(),
            contentDescription = null,
            modifier =
                Modifier
                    .size(120.dp, 120.dp),
            error = painterResource(id = R.drawable.no_internet),
        )

        // Character's list entry details
        Column(
            modifier = Modifier.padding(start = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Character's status feedback icon
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

                // Character's name
                Text(
                    text = character.name ?: stringResource(id = R.string.text_unknown),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 8.dp),
                )
            }

            // Character's details
            Text(text = "Tap for details")
        }
    }
}
