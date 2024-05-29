package com.example.mycity.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Tour
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.mycity.R
import com.example.mycity.data.Spot
import com.example.mycity.data.SpotType
import com.example.mycity.ui.utils.ContentType
import com.example.mycity.ui.utils.NavigationType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCityHomeScreen(
    navigationType: NavigationType,
    contentType: ContentType,
    cityUiState: CityUiState,
    cityViewModel: CityViewModel,
    onTabPressed : (SpotType) -> Unit,
    onSpotCardPressed : (Spot) -> Unit,
    onDetailScreenBackPressed: () ->Unit,
    modifier: Modifier =Modifier
){

    val navigationItemContentList = listOf(
        NavigationItemContent(
            spotType = SpotType.Food,
            icon = Icons.Default.Restaurant,
            text = stringResource(R.string.tab_food)
        ),
        NavigationItemContent(
            spotType = SpotType.Joy,
            icon = Icons.Default.Tour,
            text = stringResource(R.string.tab_place)
        ),
        NavigationItemContent(
            spotType = SpotType.Bookmark,
            icon = Icons.Default.Bookmark,
            text = stringResource(R.string.tab_bookmark)
        )
    )

    if(navigationType==NavigationType.PERMANENT_NAVIGATION_DRAWER){
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet(Modifier.fillMaxWidth(0.2f)){
                    NavigationDrawerContent(
                        selectedDestination = cityUiState.currentSpotType,
                        onTabPressed = onTabPressed,
                        navigationItemContentList = navigationItemContentList,
                        modifier = Modifier
                            .wrapContentWidth()
                            .fillMaxHeight()
                    )
                }
            }
        ){
            MyCityAppContent(
                navigationType= navigationType,
                contentType = contentType,
                cityUiState = cityUiState,
                cityViewModel=cityViewModel,
                onTabPressed = onTabPressed,
                onSpotCardPressed = onSpotCardPressed,
                navigationItemContentList = navigationItemContentList,
                modifier=modifier
            )
        }

    }else{
        if(cityUiState.isShowingHomePage){
            MyCityAppContent(
                navigationType= navigationType,
                contentType = contentType,
                cityUiState = cityUiState,
                cityViewModel=cityViewModel,
                onTabPressed = onTabPressed,
                onSpotCardPressed = onSpotCardPressed,
                navigationItemContentList = navigationItemContentList,
                modifier=modifier
            )
        }else{

        }
    }

}



@Composable
private fun MyCityAppContent(
    navigationType: NavigationType,
    contentType: ContentType,
    cityUiState: CityUiState,
    cityViewModel: CityViewModel,
    onTabPressed: (SpotType) -> Unit,
    onSpotCardPressed: (Spot) -> Unit,
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier=Modifier
){
    Box(modifier=modifier){
        Row(modifier = Modifier.fillMaxSize()){

            AnimatedVisibility(visible = navigationType==NavigationType.NAVIGATION_RAIL){
                SpotNavigationRail(
                    currentTab = cityUiState.currentSpotType,
                    onTabPressed = onTabPressed,
                    navigationItemContentList = navigationItemContentList
                )
            }

            Column( modifier = Modifier.fillMaxSize()) {
                if(contentType==ContentType.LIST_AND_DETAIL){

                }else{
                    //북마크된 아이템이 없는 상태에서 탭을 누르면 별도의 화면 생성
                    if(cityUiState.currentSpotType==SpotType.Bookmark&&
                        cityUiState.bookmarkList.isEmpty()){
                        BookMarkEmptyScreen(Modifier.weight(1f))
                    }else{
                        SpotListOnlyContent(
                            cityUiState = cityUiState,
                            cityViewModel=cityViewModel,
                            onSpotCardPressed = onSpotCardPressed,
                            modifier=Modifier.weight(1f)
                        )
                    }
                }
                AnimatedVisibility(visible = navigationType==NavigationType.BOTTOM_NAVIGATION){
                    SpotBottomNavigationBar(
                        currentTab = cityUiState.currentSpotType,
                        onTabPressed = onTabPressed,
                        navigationItemContentList = navigationItemContentList
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavigationDrawerContent(
    selectedDestination : SpotType,
    onTabPressed: (SpotType) -> Unit,
    navigationItemContentList : List<NavigationItemContent>,
    modifier:Modifier=Modifier
){
    Column(modifier=modifier){
        NavigationDrawerHeader(
            modifier= Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.navigation_drawer_padding))
        )
        for(naviItem in navigationItemContentList){
            NavigationDrawerItem(
                selected = selectedDestination == naviItem.spotType,
                label = {
                    Text(
                        text = naviItem.text
                    )
                },
                icon={
                    Icon(
                        imageVector = naviItem.icon,
                        contentDescription = naviItem.text
                    )
                },
                onClick = {onTabPressed(naviItem.spotType)}
            )
        }
    }
}

@Composable
private fun NavigationDrawerHeader(
    modifier:Modifier=Modifier
){
    Row(
        modifier=modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment= Alignment.CenterVertically
    ){
        Text(
            text=stringResource(R.string.app_name),
            style= MaterialTheme.typography.headlineSmall
        )
        Image(
            painter= painterResource(R.drawable.logo),
            contentDescription = null,
            modifier= Modifier
                .size(dimensionResource(R.dimen.logo_size))
                .padding(dimensionResource(R.dimen.logo_padding))
        )
    }
}

@Composable
private fun SpotNavigationRail(
    currentTab : SpotType,
    onTabPressed: (SpotType) -> Unit,
    navigationItemContentList: List<NavigationItemContent>,
    modifier : Modifier = Modifier
){
    NavigationRail(modifier=modifier){
        for(naviItem in navigationItemContentList){
            NavigationRailItem(
                selected = currentTab == naviItem.spotType,
                onClick = { onTabPressed(naviItem.spotType) },
                icon = {
                    Icon(
                        imageVector = naviItem.icon,
                        contentDescription = naviItem.text
                    )
                }
            )
        }
    }

}

@Composable
private fun SpotBottomNavigationBar(
    currentTab: SpotType,
    onTabPressed: (SpotType) -> Unit,
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier=Modifier
){
    NavigationBar(modifier=modifier){
        for(naviItem in navigationItemContentList){
            NavigationBarItem(
                selected = currentTab ==naviItem.spotType,
                onClick = { onTabPressed(naviItem.spotType) },
                icon={
                    Icon(
                        imageVector = naviItem.icon,
                        contentDescription = naviItem.text
                    )
                }
            )
        }
    }
}

private data class NavigationItemContent(
    val spotType: SpotType,
    val icon: ImageVector,
    val text:String
)

