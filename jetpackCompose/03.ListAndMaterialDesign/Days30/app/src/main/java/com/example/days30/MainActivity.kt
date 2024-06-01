package com.example.days30

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PauseCircleOutline
import androidx.compose.material.icons.outlined.PlayCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.days30.data.Datasource.allMusic
import com.example.days30.model.Music
import com.example.days30.ui.theme.Days30Theme
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Days30Theme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Days30App()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Days30App(){
    Days30List(
        allMusic
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Days30List(days30List:List<Music>){
    val currentDate =LocalDate.now().dayOfMonth
    val scrollState =rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyRow(
        state=scrollState
    ){
        itemsIndexed(days30List){ index, days30 ->
            Days30Item(index=index, music = days30)
        }
    }
    LaunchedEffect(currentDate){
        coroutineScope.launch {
            scrollState.animateScrollToItem(currentDate-1)
        }
    }
}

@Composable
fun Days30Item(index:Int, music:Music, modifier:Modifier=Modifier){
    var expanded by remember {mutableStateOf(false)}
    Column(
        modifier= Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(dimensionResource(R.dimen.padding_large))
            .wrapContentSize(Alignment.Center)
            .animateContentSize(
                animationSpec=spring(
                    dampingRatio= Spring.DampingRatioNoBouncy,
                    stiffness=Spring.StiffnessVeryLow
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text=(index+1).toString(),
            style=MaterialTheme.typography.displaySmall,
            modifier=Modifier.padding(dimensionResource(R.dimen.padding_medium))
        )

        Surface(
            shape= RectangleShape,
            shadowElevation = dimensionResource(R.dimen.shadow)
        ){
            Image(
                painter = painterResource(music.imageResourceId),
                contentDescription = stringResource(music.title),
                modifier= Modifier
                    .width(dimensionResource(R.dimen.image))
                    .height(dimensionResource(R.dimen.image)),
                contentScale=ContentScale.Crop
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier=modifier
        ) {
            if(expanded){
                MusicInformation(music)
                MusicPlayer(music)
            }
            MusicButton(
                expanded=expanded,
                onClick = {expanded=!expanded},
                index=index,
                modifier= Modifier
                    .size(dimensionResource(R.dimen.icon))
                    .padding(dimensionResource(R.dimen.padding_small))
            )
        }

    }
}

@Composable
fun MusicInformation(music:Music){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier=Modifier.padding(dimensionResource(R.dimen.padding_small))
    ){
        Text(
            text=stringResource(music.title),
            style=MaterialTheme.typography.headlineLarge,
            modifier=Modifier.padding(dimensionResource(R.dimen.padding_tiny))
        )
        Text(
            text=stringResource(music.artist),
            style=MaterialTheme.typography.titleLarge,
            modifier=Modifier.padding(dimensionResource(R.dimen.padding_tiny))
        )
        Text(
            text=stringResource(music.genre),
            style=MaterialTheme.typography.titleMedium,
            modifier=Modifier.padding(dimensionResource(R.dimen.padding_tiny))
        )
    }
}

@Composable
fun MusicPlayer(music:Music, modifier:Modifier=Modifier){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier=modifier
    ){
        Text(
            text= stringResource(R.string.music_start),
            style=MaterialTheme.typography.labelLarge
        )
        DrawLine(100f)
        Text(
            text= stringResource(music.duration),
            style=MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun DrawLine(space:Float){
    Canvas(
        modifier= Modifier
            .width(dimensionResource(R.dimen.canvas_width))
            .height(dimensionResource(R.dimen.canvas_height))
    ){
        val width=size.width
        val height=size.height/2
        drawLine(
            start= Offset(x=space, y=height),
            end=Offset(x=width-space,y=height),
            color= Color.Black,
            strokeWidth = 6f
        )
    }
}

@Composable
fun MusicButton(
    expanded:Boolean,
    onClick:()->Unit,
    index:Int,
    modifier:Modifier=Modifier
){
    IconButton(
        onClick = onClick,
        modifier=modifier
    ) {
        Icon(
            imageVector = if(expanded) Icons.Outlined.PauseCircleOutline else Icons.Outlined.PlayCircleOutline,
            contentDescription = "${index+1} index"+stringResource(R.string.icon_button),
            modifier=modifier
        )
    }
}

@Preview
@Composable
private fun Days30ItemPreview(){
    Days30Item(0,Music(R.drawable.letter, R.string.title1, R.string.artist1, R.string.genre_rock, R.string.duration1))
}


