package com.example.mycity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mycity.ui.MyCityApp
import com.example.mycity.ui.theme.MyCityTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   val windowSize=calculateWindowSizeClass(this)
                    MyCityApp(windowSize.widthSizeClass)
                }
            }
        }
    }
}

@Preview(showBackground=true)
@Composable
fun MyCityAppCompactPreview(){
    MyCityTheme {
        Surface {
            MyCityApp(windowSize = WindowWidthSizeClass.Compact)
        }
    }
}

@Preview(showBackground=true, widthDp = 700)
@Composable
fun MyCityAppMediumPreview(){
    MyCityTheme {
        Surface {
            MyCityApp(windowSize = WindowWidthSizeClass.Medium)
        }
    }
}

@Preview(showBackground=true, widthDp = 1000)
@Composable
fun MyCityAppExpandedPreview(){
    MyCityTheme {
        Surface {
            MyCityApp(windowSize = WindowWidthSizeClass.Expanded)
        }
    }
}

