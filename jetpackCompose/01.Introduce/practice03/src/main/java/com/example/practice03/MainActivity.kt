package com.example.practice03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.practice03.ui.theme.IntroduceTheme

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
                    Quadrant()
                }
            }
        }
    }
}

@Composable
fun Quadrant() {
    Column {
        Row(Modifier.weight(1f)) {
            Section(
                title = stringResource(R.string.title1),
                content = stringResource(id = R.string.content1),
                backgroundColor = Color(0XFFEADDFF),
                modifier = Modifier.weight(1f)
            )
            Section(
                title = stringResource(R.string.title2),
                content = stringResource(id = R.string.content2),
                backgroundColor = Color(0XFFD0BCFF),
                modifier = Modifier.weight(1f)
            )
        }
        Row(Modifier.weight(1f)) {
            Section(
                title = stringResource(R.string.title3),
                content = stringResource(id = R.string.content3),
                backgroundColor = Color(0XFFB69DF8),
                modifier = Modifier.weight(1f)
            )
            Section(
                title = stringResource(R.string.title4),
                content = stringResource(id = R.string.content4),
                backgroundColor = Color(0xFFF6EDFF),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun Section(
    title: String,
    content: String,
    backgroundColor: Color,
    modifier: Modifier=Modifier
) {

    Column(
        modifier= modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontWeight= FontWeight.Bold,
            modifier=Modifier.padding(bottom=16.dp)
        )
        Text(
            text = content,
            textAlign= TextAlign.Justify
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IntroduceTheme {
        Quadrant()
    }
}