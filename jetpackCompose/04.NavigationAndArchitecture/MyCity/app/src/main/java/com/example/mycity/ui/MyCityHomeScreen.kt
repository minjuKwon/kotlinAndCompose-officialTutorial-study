package com.example.mycity.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
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
                PermanentDrawerSheet(Modifier.fillMaxWidth(0.22f)){
                    NavigationDrawerContent(
                        selectedDestination = cityUiState.currentSpotType,
                        onTabPressed = onTabPressed,
                        navigationItemContentList = navigationItemContentList,
                        modifier = Modifier
                            .wrapContentWidth()
                            .fillMaxHeight()
                            .padding(
                                dimensionResource(R.dimen.permanent_navigation_drawer_padding)
                            )
                    )
                }
            },modifier=Modifier.testTag(stringResource(R.string.navigation_drawer))
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
            CityDetailScreen(
                cityUiState = cityUiState,
                onBackPressed = onDetailScreenBackPressed,
                isFullScreen = true,
                modifier=modifier
            )
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
                    navigationItemContentList = navigationItemContentList,
                    modifier=Modifier.testTag(stringResource(R.string.navigation_rail))
                )
            }

            Column( modifier = Modifier.fillMaxSize()) {
                if(contentType==ContentType.LIST_AND_DETAIL){
                    SpotListAndDetailContent(
                        cityUiState = cityUiState,
                        cityViewModel = cityViewModel,
                        onSpotCardPressed = onSpotCardPressed,
                        modifier=Modifier.weight(1f)
                    )
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
                            modifier= Modifier
                                .weight(1f)
                        )
                    }
                }
                AnimatedVisibility(visible = navigationType==NavigationType.BOTTOM_NAVIGATION){
                    SpotBottomNavigationBar(
                        currentTab = cityUiState.currentSpotType,
                        onTabPressed = onTabPressed,
                        navigationItemContentList = navigationItemContentList,
                        modifier=Modifier.fillMaxWidth()
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
                .padding(bottom=dimensionResource(R.dimen.navigation_drawer_header_padding))
                .background(Color.White)
        )
        for(naviItem in navigationItemContentList){
            NavigationDrawerItem(
                selected = selectedDestination == naviItem.spotType,
                label = {
                    Text(
                        text = naviItem.text,
                        modifier=Modifier.padding(
                            start= dimensionResource(
                                R.dimen.navigation_drawer_item_text_start_padding
                            )
                        )
                    )
                },
                icon={
                    Icon(
                        imageVector = naviItem.icon,
                        contentDescription = naviItem.text,
                        modifier=Modifier.padding(
                            start= dimensionResource(
                                R.dimen.navigation_drawer_item_icon_start_padding
                            )
                        )
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
            style= MaterialTheme.typography.titleLarge
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
        Column(
            modifier=Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
            ) {
            for(naviItem in navigationItemContentList){
                NavigationRailItem(
                    selected = currentTab == naviItem.spotType,
                    onClick = { onTabPressed(naviItem.spotType) },
                    icon = {
                        Icon(
                            imageVector = naviItem.icon,
                            contentDescription = naviItem.text
                        )
                    },
                    modifier=Modifier.padding(
                        top= dimensionResource(R.dimen.navigation_rail_item_top_padding)
                    )
                )
            }
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
    NavigationBar(
        modifier=modifier.testTag(stringResource(R.string.navigation_bottom))
    ){
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

