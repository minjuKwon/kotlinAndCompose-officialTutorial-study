package com.example.flightsearch.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.flightsearch.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchScreen(

){
    Column {
        TextField(
            value = "",
            onValueChange = {},
            label={Text(stringResource(R.string.search_label))},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search)
                )
            },
            modifier=Modifier.fillMaxWidth()
        )
        Text(text="")
        LazyColumn{}
    }
}

@Composable
fun FlightSearchItem(

){
    Card{
        Row{
            Column {
                Text(text=stringResource(R.string.depart))
                Row{
                    Text(text="")
                    Text(text="")
                }
                Text(text=stringResource(R.string.depart))
                Row{
                    Text(text="")
                    Text(text="")
                }
            }
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = stringResource(R.string.bookmark)
            )
        }
    }
}

@Composable
fun FlightSearchRecommendItem(

){
    Row{
        Text(text="")
        Text(text="")
    }
}