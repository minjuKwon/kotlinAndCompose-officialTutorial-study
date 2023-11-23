package com.example.amphibians.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.amphibians.R
import com.example.amphibians.network.Amphibians
import com.example.amphibians.ui.theme.AmphibiansTheme

@Composable
fun HomeScreen(
    amphibiansUiState: AmphibiansUiState,
    retryAction: () -> Unit,
    modifier: Modifier= Modifier,
){
    when(amphibiansUiState){
        is AmphibiansUiState.Loading -> LoadingScreen(modifier=modifier.fillMaxSize())
        is AmphibiansUiState.Success -> AmphibiansGridScreen(amphibians=amphibiansUiState.list, modifier=modifier.padding(12.dp))
        is AmphibiansUiState.Error -> ErrorScreen(retryAction, modifier=modifier.fillMaxSize())
    }
}

@Composable
fun LoadingScreen(modifier:Modifier=Modifier){
    Image(
        painter= painterResource(id = R.drawable.baseline_rotate_left),
        contentDescription = stringResource(id = R.string.loading),
        modifier=modifier.size(200.dp)
    )
}

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier=Modifier
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter= painterResource(id = R.drawable.baseline_error_outline),
            contentDescription = stringResource(id = R.string.error)
        )
        Text(text = stringResource(id = R.string.error), modifier=Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(id = R.string.retry))
        }
    }
}

@Composable
fun AmphibiansCard(amphibians: Amphibians, modifier: Modifier=Modifier) {
    Card(
        modifier=modifier
    ){
        Column {
            Text(
                text=amphibians.name+"("+amphibians.type+")",
                style=MaterialTheme.typography.titleLarge
            )
            AsyncImage(
                model = ImageRequest.Builder(context=LocalContext.current)
                    .data(amphibians.imgSrc)
                    .crossfade(true)
                    .build(),
                error= painterResource(id = R.drawable.baseline_broken_image),
                placeholder = painterResource(id = R.drawable.baseline_rotate_left),
                contentDescription = stringResource(id = R.string.img),
                contentScale = ContentScale.Crop,
                modifier= Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
            Text(
                text=amphibians.description
            )

        }
    }

}

@Composable
fun AmphibiansGridScreen(
    amphibians: List<Amphibians>,
    modifier: Modifier=Modifier
) {
    LazyVerticalGrid(
        columns= GridCells.Fixed(1),
        modifier=modifier.fillMaxWidth()
    ){
        items(items=amphibians){
            amphibian->
            AmphibiansCard(
                amphibians =amphibian,
                modifier= modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground=true)
@Composable
fun AmphibiansCardPreview() {
    AmphibiansTheme {
        val amphibians=Amphibians(
            name="Great Basin Spadefoot",
            type="Toad",
            description ="This toad spends most of its life underground due to the arid desert conditions in which it lives. Spadefoot toads earn the name because of their hind legs which are wedged to aid in digging. They are typically grey, green, or brown with dark spots." ,
            imgSrc = "https://developer.android.com/codelabs/basic-android-kotlin-compose-amphibians-app/img/great-basin-spadefoot.png"
        )
        AmphibiansCard(amphibians=amphibians)
    }
}

@Preview(showBackground=true)
@Composable
fun AmphibiansGridScreenPreview() {
    AmphibiansTheme {
        val mockData=List(10){
            Amphibians(
                name="Great Basin Spadefoot",
                type="Toad",
                description ="This toad spends most of its life underground due to the arid desert conditions in which it lives. Spadefoot toads earn the name because of their hind legs which are wedged to aid in digging. They are typically grey, green, or brown with dark spots." ,
                imgSrc = "https://developer.android.com/codelabs/basic-android-kotlin-compose-amphibians-app/img/great-basin-spadefoot.png"
            )
        }
        AmphibiansGridScreen(amphibians = mockData)
    }
}
