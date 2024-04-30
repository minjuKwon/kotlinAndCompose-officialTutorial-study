package com.example.listandmaterialdesign

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.listandmaterialdesign.data.DataSource
import com.example.listandmaterialdesign.model.Topic
import com.example.listandmaterialdesign.ui.theme.ListAndMaterialDesignTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListAndMaterialDesignTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CourseApp()
                }
            }
        }
    }
}

@Composable
fun CourseApp() {
    CourseList(topicList = DataSource.topics)
}

@Composable
fun CourseCard(topic: Topic, modifier: Modifier=Modifier) {
    Card(modifier=Modifier.height(68.dp)){
        Row(modifier=modifier){

            Image(
                painter= painterResource(topic.imageResourceId),
                contentDescription = stringResource(topic.titleResourceId),
                modifier= Modifier
                    .fillMaxHeight()
                    .width(68.dp)
            )

            Column(modifier=Modifier.padding(top=16.dp, start = 16.dp, end=16.dp)){
                Text(
                    stringResource(topic.titleResourceId),
                    style=MaterialTheme.typography.labelMedium
                )
                Row(modifier=Modifier.padding(top=8.dp)){
                    Image(
                        painter = painterResource(R.drawable.ic_grain),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.Black)
                    )
                    Text(
                        text=topic.count.toString(),
                        style = MaterialTheme.typography.labelMedium,
                        modifier=Modifier.padding(start=8.dp)
                    )

                }
            }

        }

    }
}

@Composable
fun CourseList(topicList: List<Topic>, modifier:Modifier=Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier=modifier,
        contentPadding=PaddingValues(8.dp,8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(items=topicList){ topic->
            CourseCard(
                topic
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ListAndMaterialDesignTheme {
       CourseCard( Topic(R.string.architecture, 58, R.drawable.architecture))
    }
}