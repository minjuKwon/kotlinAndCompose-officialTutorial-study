package com.example.mycity.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.mycity.R
import com.example.mycity.data.Spot

@Composable
fun CityDetailScreen(
    cityUiState: CityUiState,
    onBackPressed: ()->Unit,
    modifier:Modifier= Modifier,
    isFullScreen:Boolean=false
){

    BackHandler {
        onBackPressed()
    }

    Column(modifier=modifier) {
        if(isFullScreen){
            CityDetailsScreenTopBar(
                onBackButtonClicked = onBackPressed,
                cityUiState = cityUiState,
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = dimensionResource(R.dimen.topBar_detail_bottom_padding)
                    )
                    .background(Color.White)
            )
        }
        LazyColumn(
            modifier=Modifier.fillMaxSize()
        ){
            item {
                CityDetailsCard(
                    spot = cityUiState.currentSelectSpot,
                    modifier=Modifier.padding(
                        dimensionResource(R.dimen.card_detail_outer_padding)
                    )
                )
            }
        }
    }

}

@Composable
private fun CityDetailsScreenTopBar(
    onBackButtonClicked: ()->Unit,
    cityUiState: CityUiState,
    modifier:Modifier=Modifier
){
    Row(
        modifier=modifier,
        verticalAlignment = Alignment.CenterVertically
    ){

        IconButton(
            onClick = onBackButtonClicked,
            modifier=Modifier.padding(dimensionResource(R.dimen.navigation_back_padding))
        ){
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.navigation_back)
            )
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text= stringResource(cityUiState.currentSelectSpot.name),
                style = MaterialTheme.typography.headlineMedium
            )
        }

    }
}

@Composable
private fun CityDetailsCard(
    spot: Spot,
    modifier:Modifier=Modifier
){
    Card(
        modifier=modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ){
        Column(modifier = Modifier
            .fillMaxWidth()
        ) {

            Image(
                painter = painterResource(spot.img),
                contentDescription = null,
            )

            Column(modifier=Modifier
                .padding(dimensionResource(R.dimen.card_detail_inner_padding)))
            {
                CityDetailsCardText(
                    title = stringResource(R.string.name),
                    content = stringResource(spot.name)
                )

                CityDetailsCardText(
                    title = stringResource(R.string.type),
                    content = stringResource(spot.type)
                )

                CityDetailsCardText(
                    title = stringResource(R.string.location),
                    content = stringResource(spot.location)
                )

                CityDetailsCardText(
                    title = stringResource(R.string.duration),
                    content = stringResource(spot.duration)
                )

                CityDetailsCardText(
                    title = stringResource(R.string.description),
                    content = stringResource(spot.description)
                )
            }

        }
    }
}

@Composable
private fun CityDetailsCardText(
    title:String,
    content:String,
    modifier:Modifier=Modifier
){
    Row(
        modifier=modifier
    ){
        Text(
            text=title,
            modifier=Modifier.padding(
                dimensionResource(R.dimen.card_detail_text_padding)
            ),
            color=Color.Black
        )
        Text(
            text= stringResource(R.string.spilt),
            modifier=Modifier.padding(
                 dimensionResource(R.dimen.card_detail_text_padding)
            ),
            color=Color.Black
        )
        Text(
            text=content,
            modifier=Modifier.padding(
                dimensionResource(R.dimen.card_detail_text_padding)
            ),
            color=Color.Black
        )
    }
}