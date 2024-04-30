package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.BuildingUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BuildingUITheme {
                showLemonAde()
            }
        }
    }
}

@Composable
fun lemonAde() {
    var step by remember {mutableStateOf(1)}
    var squeeze by remember{mutableStateOf(2)}

    Surface(modifier=Modifier.fillMaxSize()) {
        when(step){
            1->{
                LemonImgAndTxt(
                    imgResourceId = R.drawable.lemon_tree,
                    txtResourceId = R.string.lemon_tree_content,
                    contentDescriptionId = R.string.lemon_tree_content_description,
                    onImgClick = {
                        step = 2
                        squeeze=(2..4).random()}
                )
            }
            2->{
                LemonImgAndTxt(
                    imgResourceId = R.drawable.lemon_squeeze,
                    txtResourceId = R.string.lemon_squeeze_content,
                    contentDescriptionId = R.string.lemon_squeeze_content_description,
                    onImgClick = {
                        squeeze--
                        if(squeeze==0){
                            step = 3
                        }
                    }
                )
            }
            3->{
                LemonImgAndTxt(
                    imgResourceId = R.drawable.lemon_drink,
                    txtResourceId = R.string.lemon_drink_content,
                    contentDescriptionId = R.string.lemon_drink_content_description,
                    onImgClick = { step = 4 }
                )
            }
            4->{
                LemonImgAndTxt(
                    imgResourceId = R.drawable.lemon_restart,
                    txtResourceId = R.string.lemon_restart_content,
                    contentDescriptionId = R.string.lemon_restart_content_description,
                    onImgClick = { step = 1 }
                )
            }
        }
    }

}

@Composable
fun LemonImgAndTxt(
    imgResourceId: Int,
    txtResourceId: Int,
    contentDescriptionId: Int,
    onImgClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier=Modifier.fillMaxSize()
    ) {
        Button(
            onClick = onImgClick,
            shape= RoundedCornerShape(40.dp),
            colors = ButtonDefaults.buttonColors(containerColor=Color(0XFFbce3d0))
        ) {
            Image(
                painter= painterResource(id = imgResourceId),
                contentDescription = stringResource(id = contentDescriptionId)
            )
        }
        Spacer(modifier=Modifier.height(16.dp))
        Text(
            text = stringResource(id = txtResourceId),
            fontSize=18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun showLemonAde() {
    lemonAde()
}
