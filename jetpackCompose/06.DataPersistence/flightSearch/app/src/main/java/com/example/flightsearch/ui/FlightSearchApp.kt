package com.example.flightsearch.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.example.flightsearch.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchApp(
    modifier:Modifier=Modifier
){
    val scrollBehavior= TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier=modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar={
            FlightSearchTopBar(
                title = stringResource(R.string.app_name),
                scrollBehavior = scrollBehavior
            )
        }
    ){ innerPadding->
        FlightSearchScreen(
            modifier=Modifier.padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchTopBar(
    title:String,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
){
    TopAppBar(
        title= { Text(title) },
        scrollBehavior = scrollBehavior,
        modifier=modifier
    )
}
