package com.example.superheroes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.superheroes.model.HeroesRepository
import com.example.superheroes.ui.theme.SuperheroesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperheroesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SuperHeroesApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperHeroesApp() {
    Scaffold(
        topBar={ SuperHeroesAppBar()}
    ){
        HeroList(heroList = HeroesRepository.heroes,it)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperHeroesAppBar(modifier: Modifier=Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                stringResource(R.string.app_name),
                style=MaterialTheme.typography.displayLarge
            )
        }, modifier=modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SuperheroesTheme() {
        SuperHeroesApp()
    }
}