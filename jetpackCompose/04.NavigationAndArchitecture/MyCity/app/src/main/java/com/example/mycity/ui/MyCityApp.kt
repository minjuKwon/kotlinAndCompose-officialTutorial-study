package com.example.mycity.ui


import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycity.data.Spot
import com.example.mycity.data.SpotType
import com.example.mycity.ui.utils.ContentType
import com.example.mycity.ui.utils.NavigationType

@Composable
fun MyCityApp(
    windowSize : WindowWidthSizeClass,
    modifier: Modifier= Modifier
){
    val viewModel:CityViewModel = viewModel()
    val cityUiState = viewModel.uiState.collectAsState().value

    val navigationType: NavigationType
    val contentType : ContentType

    when(windowSize){
        WindowWidthSizeClass.Compact->{
            navigationType = NavigationType.BOTTOM_NAVIGATION
            contentType = ContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Medium->{
            navigationType = NavigationType.NAVIGATION_RAIL
            contentType = ContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Expanded->{
            navigationType = NavigationType.PERMANENT_NAVIGATION_DRAWER
            contentType = ContentType.LIST_AND_DETAIL
        }
        else->{
            navigationType = NavigationType.BOTTOM_NAVIGATION
            contentType = ContentType.LIST_ONLY
        }
    }

    MyCityHomeScreen(
        navigationType = navigationType,
        contentType = contentType,
        cityUiState = cityUiState,
        onTabPressed = {spotType:SpotType->
            viewModel.updateCurrentSpotType(spotType)

        },

        onSpotCardPressed = {spot: Spot ->
            viewModel.updateDetailScreenStates(spot)
        },
        onDetailScreenBackPressed = {

        },
        modifier=modifier
    )

}

fun pressedTab(
    viewModel: CityViewModel,
    cityUiState: CityUiState,
    spotType: SpotType
){
    if(spotType==SpotType.Bookmark){
        if(cityUiState.bookmarkList.size>0){
            viewModel.resetBookmarkScreen()
        }else{

        }
    }else{
        viewModel.resetHomeScreenStates()
    }
}