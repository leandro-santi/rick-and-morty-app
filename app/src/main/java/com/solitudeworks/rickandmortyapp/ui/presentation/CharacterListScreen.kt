package com.solitudeworks.rickandmortyapp.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.solitudeworks.rickandmortyapp.R

@Composable
fun CharacterListScreen(
    navController: NavController
) {

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxHeight()
    ) {
        Column {
            Column {
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painter = painterResource(id = R.drawable.rick_morty),
                    contentDescription = "Rick And Morty Logo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {

                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { /* Lógica do primeiro botão */ },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text("Alive")
                }
                Button(
                    onClick = { /* Lógica do segundo botão */ },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Text("Dead")
                }
                Button(
                    onClick = { /* Lógica do terceiro botão */ },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Text("Unknown")
                }
            }
        }
    }

}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(10.dp))
                .background(Color.White, RoundedCornerShape(10.dp))
                .padding(horizontal = 20.dp, vertical = 12.dp)
        )
    }
}