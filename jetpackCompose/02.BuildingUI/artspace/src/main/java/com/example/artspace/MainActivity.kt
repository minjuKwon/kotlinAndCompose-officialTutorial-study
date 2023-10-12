package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.BuildingUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BuildingUITheme {
                ArtSpacePreview()
            }
        }
    }
}

@Composable
fun ArtSpace() {

    var step by remember {mutableStateOf(1)}

    Column(
        modifier= Modifier
            .fillMaxSize()
                      .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier=Modifier.height(100.dp))

        when(step){
            1->{
                Art(R.drawable.vincent,R.string.title1)
                Spacer(modifier=Modifier.height(80.dp))
                ContentDescription(R.string.title1, R.string.content1)
            }
            2->{
                Art(R.drawable.monet,R.string.title2)
                Spacer(modifier=Modifier.height(80.dp))
                ContentDescription(R.string.title2, R.string.content2)
            }
            3->{
                Art(R.drawable.mucha,R.string.title3)
                Spacer(modifier=Modifier.height(80.dp))
                ContentDescription(R.string.title3, R.string.content3)
            }
        }

        Row(modifier=Modifier.padding(40.dp)) {
            Button(
                onClick={
                    if(step==1){
                        step=3
                    }else{
                        step--
                    }
                }
            ){
                Text(
                    text=stringResource(R.string.previous),
                    fontSize=12.sp,
                    fontWeight= FontWeight.Light
                )
            }
            Spacer(modifier=Modifier.width(80.dp))
            Button(
                onClick={
                    if(step==3){
                        step=1
                    }else{
                        step++
                    }
                }
            ){
                Text(
                    text=stringResource(R.string.next),
                    fontSize=12.sp,
                    fontWeight= FontWeight.Light
                )
            }
        }

    }

}

@Composable
fun Art(
    @DrawableRes imageResource:Int,
    @StringRes imageDescription:Int
) {
    Surface(
        border=BorderStroke(20.dp,Color.White),
        shape= RectangleShape,
        shadowElevation = 16.dp
    ) {
        Image(
            painter= painterResource(id = imageResource),
            contentDescription = stringResource(id = imageDescription),
            modifier= Modifier
                .height(360.dp)
                .width(300.dp)
        )
    }
}

@Composable
fun ContentDescription(
    @StringRes textTitle: Int,
    @StringRes textContent: Int,
) {
    Column(
        modifier= Modifier
            .background(Color.LightGray)
            .width(300.dp)
            .padding(16.dp),
      verticalArrangement = Arrangement.Center
    ) {
        Text(
            text= stringResource(id = textTitle),
            fontSize=24.sp,
            fontWeight= FontWeight.Thin
        )
        Text(
            text= stringResource(id = textContent),
            fontWeight= FontWeight.Bold
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ArtSpacePreview() {
    ArtSpace()
}