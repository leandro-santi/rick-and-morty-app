package com.solitudeworks.rickandmortyapp.ui.presentation

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
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.solitudeworks.rickandmortyapp.R
import com.solitudeworks.rickandmortyapp.data.response.CharacterDetail
import com.solitudeworks.rickandmortyapp.utils.RickAndMortyScreenTitles

@Composable
fun CharacterItem(character: CharacterDetail, navController: NavHostController) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate(RickAndMortyScreenTitles.SingleCharacter.name + "/${character.id}/${character.name}")
            }, horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(character.image)
                .crossfade(true)
                .crossfade(1000)
                .build(), contentDescription = null, modifier = Modifier
                .size(200.dp, 200.dp), error = painterResource(id = R.drawable.no_internet)
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = character.name ?: stringResource(id = R.string.text_unknown),
                fontSize = 20.sp,
                textAlign = TextAlign.Start
            )
            Row(
                modifier = Modifier.padding(top = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(
                        id = when (character.status) {
                            "Alive" -> R.drawable.ic_dot_green
                            "Dead" -> R.drawable.ic_dot_red
                            "unknown" -> R.drawable.ic_dot_grey
                            else -> R.drawable.ic_dot_grey
                        }
                    ), contentDescription = null, tint = Color.Unspecified
                )
                Text(text = "  ${character.status ?: ""} - ${character.species}")
            }

            Text(text = context.getString(R.string.text_last_known_location), color = Color.Gray)
            // Text(text = character.characterLocation?.name ?: "")
            Text(text = "")
        }
    }
}

@Composable
fun CharacterItemPreview() {
    Column {
        CharacterItem(
            character = CharacterDetail(
                null,
                null,
                null,
                1,
                "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                null,
                "Rick",
                null,
                "Human",
                null,
                null,
                null
            ), rememberNavController()
        )

        CharacterItem(
            character = CharacterDetail(
                null,
                null,
                null,
                1,
                "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                null,
                "Rick",
                null,
                "Human",
                null,
                null,
                null
            ), rememberNavController()
        )

        CharacterItem(
            character = CharacterDetail(
                null,
                null,
                null,
                1,
                "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                null,
                "Rick",
                null,
                "Human",
                null,
                null,
                null
            ), rememberNavController()
        )

    }
}
