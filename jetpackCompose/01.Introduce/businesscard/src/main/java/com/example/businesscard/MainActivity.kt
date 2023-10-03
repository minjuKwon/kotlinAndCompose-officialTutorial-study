package com.example.businesscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscard.ui.theme.IntroduceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntroduceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Card()
                }
            }
        }
    }
}

@Composable
fun Card(
) {

    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFC8E3C8)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            Modifier.weight(4f),
            verticalArrangement = Arrangement.Center,) {
            Owner(
                img = painterResource(R.drawable.android_logo),
                name = stringResource(R.string.name),
                title = stringResource(R.string.title),
                textColor = Color(0xFF167311),
                imgColor = Color(0XFF161C4F)
            )
        }

        Column(Modifier.weight(1f)) {
            Contact(
                img = Icons.Rounded.Call,
                content = stringResource(R.string.phone),
                color = Color(0xFF167311)
            )
            Contact(
                img = Icons.Rounded.Share,
                content = stringResource(R.string.id),
                color = Color(0xFF167311)
            )
            Contact(
                img = Icons.Rounded.Email,
                content = stringResource(R.string.email),
                color = Color(0xFF167311)
            )
        }
    }

}

@Composable
fun Owner(
    img: Painter,
    name: String,
    title: String,
    textColor:Color,
    imgColor:Color,
    modifier:Modifier=Modifier
    ) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier=modifier
    ) {
        Image(
            painter = img,
            contentDescription = null,
            modifier= Modifier
                .background(imgColor)
                .height(150.dp)
                .width(150.dp)
                .padding(10.dp)
        )

        Text(
            text = name,
            fontSize = 48.sp,
            fontWeight = FontWeight.Light,
            modifier=Modifier.padding(top=4.dp)
        )
        Text(
            text = title,
            color= textColor,
            fontWeight = FontWeight.Bold,
            modifier=Modifier.padding(8.dp)
        )
    }

}

@Composable
fun Contact(
    img: ImageVector,
    content: String,
    color: Color,
    modifier:Modifier=Modifier
    ) {

    Row(modifier=modifier) {
        Icon(
            img,
            tint= color,
            contentDescription = null,
            modifier=Modifier.padding(8.dp)
        )

        Text(
            text = content,
            modifier=Modifier.padding(8.dp)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IntroduceTheme {
        Card()
    }
}