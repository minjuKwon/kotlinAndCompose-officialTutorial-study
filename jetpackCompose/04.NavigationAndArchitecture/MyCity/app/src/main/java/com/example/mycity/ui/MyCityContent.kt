package com.example.mycity.ui

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.mycity.R
import com.example.mycity.data.Spot
import com.example.mycity.data.SpotType

@Composable
fun SpotListOnlyContent(
    cityUiState: CityUiState,
    cityViewModel: CityViewModel,
    onSpotCardPressed: (Spot) -> Unit,
    modifier: Modifier =Modifier
){
    LazyColumn(
        modifier=modifier,
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.list_card_padding)
        )
    ){

        item{
            SpotHomeTopBar(modifier= Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(R.dimen.topBar_top_padding),
                    bottom = dimensionResource(R.dimen.topBar_bottom_padding)
                )
            )
        }

        if(cityUiState.currentSpotType==SpotType.Bookmark){
            items(cityUiState.bookmarkList, key = { it.id }){spot->
                var pressedBookmark by remember {mutableStateOf(spot.isBookmark)}
                SpotListItem(
                    spot = spot,
                    pressed = pressedBookmark,
                    onCardClick = {onSpotCardPressed(spot)},
                    onBookmarkClick = {
                        pressedBookmark=!pressedBookmark
                        cityViewModel.updateBookmarkList(spot)
                    }

                )
            }
        }
        else{
            val spots=cityUiState.currentSpotTypeList
            items(spots, key = { it.id }){spot->
                var pressedBookmark by remember {mutableStateOf(spot.isBookmark)}
                SpotListItem(
                    spot = spot,
                    pressed = pressedBookmark,
                    onCardClick = {onSpotCardPressed(spot)},
                    onBookmarkClick = {
                        pressedBookmark=!pressedBookmark
                        cityViewModel.updateBookmarkList(spot)
                    }
                )
            }
        }

    }


}

@Composable
fun SpotListAndDetailContent(
    cityUiState: CityUiState,
    cityViewModel: CityViewModel,
    onSpotCardPressed: (Spot) -> Unit,
    modifier: Modifier=Modifier
){
    Row(modifier=modifier){

        LazyColumn(
            modifier= Modifier
                .weight(1f)
                .padding(
                    top = dimensionResource(R.dimen.list_and_detail_top_padding),
                    end = dimensionResource(R.dimen.list_and_detail_end_padding)
                ),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.list_card_padding)
            )

        ){
            if(cityUiState.currentSpotType==SpotType.Bookmark){
                items(cityUiState.bookmarkList, key = { it.id }){spot->
                    var pressedBookmark by remember {mutableStateOf(spot.isBookmark)}
                    SpotListItem(
                        spot = spot,
                        pressed = pressedBookmark,
                        onCardClick = {onSpotCardPressed(spot)},
                        onBookmarkClick = {
                            pressedBookmark=!pressedBookmark
                            cityViewModel.updateBookmarkList(spot)
                        }

                    )
                }
            }
            else{
                val spots=cityUiState.currentSpotTypeList
                items(spots, key = { it.id }){spot->
                    var pressedBookmark by remember {mutableStateOf(spot.isBookmark)}
                    SpotListItem(
                        spot = spot,
                        pressed = pressedBookmark,
                        onCardClick = {onSpotCardPressed(spot)},
                        onBookmarkClick = {
                            pressedBookmark=!pressedBookmark
                            cityViewModel.updateBookmarkList(spot)
                        }
                    )
                }
            }
        }

        val activity = LocalContext.current as Activity
        CityDetailScreen(
            cityUiState = cityUiState,
            onBackPressed = {activity.finish() },
            modifier=Modifier.weight(1f)
        )
    }
}

@Composable
fun BookMarkEmptyScreen(modifier:Modifier=Modifier){
    Column(
        modifier=modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpotHomeTopBar(
            modifier=Modifier
            .fillMaxWidth()
            .padding(
                top = dimensionResource(R.dimen.topBar_top_padding),
                bottom = dimensionResource(R.dimen.topBar_bottom_padding)
            ))
        Box(
            modifier= Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(
                        topStart = dimensionResource(R.dimen.empty_bookmarkList_background_round),
                        topEnd = dimensionResource(R.dimen.empty_bookmarkList_background_round)
                    )
                ),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = stringResource(R.string.empty_bookmark),
                style=MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpotListItem(
    spot:Spot,
    pressed:Boolean,
    onCardClick:()->Unit,
    onBookmarkClick:(Spot)->Unit,
    modifier:Modifier=Modifier
){

    Card(
        modifier=modifier,
        onClick=onCardClick
    ){
        Row(
            modifier=Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(spot.img),
                contentDescription = null,
                modifier=Modifier.fillMaxSize(0.3f)
            )

            Column(
                modifier=Modifier.padding(
                    start= dimensionResource(R.dimen.list_card_text_row_start_padding)
                ),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(spot.name),
                    modifier=Modifier.padding(
                        bottom= dimensionResource( R.dimen.list_card_text_bottom_padding)
                    ),
                    style=MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(spot.type),
                    modifier=Modifier.padding(
                        bottom= dimensionResource(R.dimen.list_card_text_bottom_padding)
                    ),
                    style=MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(spot.location),
                    style=MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier=Modifier.weight(1f))

            IconButton(
                onClick = { onBookmarkClick(spot)},
                modifier=Modifier.padding(
                    end= dimensionResource(R.dimen.list_card_bookmark_button_end_padding)
                )
            ) {
                Icon(
                    imageVector =
                    if(pressed) Icons.Default.Bookmark
                    else Icons.Default.BookmarkBorder,
                    contentDescription = stringResource(R.string.move_to_bookmark)
                )
            }

        }

    }

}

@Composable
private fun SpotHomeTopBar(modifier:Modifier=Modifier){
    Row(
        modifier= modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment= Alignment.CenterVertically
    ){
        Text(
            text= stringResource(R.string.app_name),
            style= MaterialTheme.typography.headlineMedium
        )
        Image(
            painter= painterResource(R.drawable.logo),
            contentDescription = null,
            modifier= Modifier
                .size(dimensionResource(R.dimen.logo_size))
                .padding(dimensionResource(R.dimen.logo_padding))
        )
    }
}
