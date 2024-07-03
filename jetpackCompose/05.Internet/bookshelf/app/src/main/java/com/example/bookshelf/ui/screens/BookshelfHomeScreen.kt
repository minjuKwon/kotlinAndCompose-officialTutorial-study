package com.example.bookshelf.ui.screens

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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.bookshelf.R
import com.example.bookshelf.checkBookList
import com.example.bookshelf.checkBookmarkIsEmpty
import com.example.bookshelf.checkTabPressed
import com.example.bookshelf.data.BookType
import com.example.bookshelf.network.Book
import com.example.bookshelf.network.BookInfo
import com.example.bookshelf.ui.BookshelfUiState
import com.example.bookshelf.ui.utils.ContentType
import com.example.bookshelf.ui.utils.NavigationType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfHomeScreen(
    bookshelfUiState: BookshelfUiState,
    onTabPressed: (BookType) -> Unit,
    onSearch:(String)->Unit,
    onBookItemPressed: (BookInfo) -> Unit,
    onBackPressed:()->Unit,
    onBookmarkPressed:(Book)->Unit,
    navigationType: NavigationType,
    contentType: ContentType,
    currentPage:Int,
    updatePage:(Int)->Unit,
    modifier:Modifier=Modifier,
    scrollState: LazyListState
){

    val navigationItemContentList = listOf(
        NavigationItemContent(
            bookType=BookType.Books,
            icon= Icons.Filled.Book,
            text= stringResource(R.string.book)
        ),
        NavigationItemContent(
            bookType=BookType.Bookmark,
            icon=Icons.Filled.Bookmark,
            text=stringResource(R.string.bookmark)
        )
    )

    if(navigationType==NavigationType.PERMANENT_NAVIGATION_DRAWER){
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet(modifier = Modifier.fillMaxWidth(0.2f)) {
                    NavigationDrawerContent(
                        selectedTab = checkTabPressed(bookshelfUiState) ,
                        onTabPressed = onTabPressed,
                        navigationItemList = navigationItemContentList,
                        modifier= Modifier
                            .fillMaxHeight()
                            .wrapContentWidth()
                            .padding(
                                dimensionResource(
                                    R.dimen.permanent_navigation_drawer_content_padding
                                )
                            )
                    )
                }

            }
        ) {
            BookshelfAppContent(
                navigationType = navigationType,
                contentType = contentType,
                bookshelfUiState = bookshelfUiState,
                onTabPressed=onTabPressed,
                onSearch=onSearch,
                onBookItemPressed=onBookItemPressed,
                onBookmarkPressed=onBookmarkPressed,
                navigationItemContent = navigationItemContentList,
                currentPage=currentPage,
                updatePage=updatePage,
                modifier = modifier,
                scrollState=scrollState
            )
        }
    }else{
        when(bookshelfUiState){
            is BookshelfUiState.Success -> {
                if(bookshelfUiState.isShowingHomepage) {
                    BookshelfAppContent(
                        navigationType=navigationType,
                        contentType = contentType,
                        bookshelfUiState = bookshelfUiState,
                        onTabPressed= onTabPressed,
                        onSearch=onSearch,
                        onBookItemPressed=onBookItemPressed,
                        onBookmarkPressed=onBookmarkPressed,
                        navigationItemContent = navigationItemContentList,
                        currentPage=currentPage,
                        updatePage=updatePage,
                        modifier = modifier,
                        scrollState=scrollState
                    )
                }
                else {BookshelfDetailsScreen(bookshelfUiState.currentItem,onBackPressed,modifier)}
            }
            else ->{
                BookshelfAppContent(
                    navigationType = navigationType,
                    contentType = contentType,
                    bookshelfUiState = bookshelfUiState,
                    onTabPressed=onTabPressed,
                    onSearch=onSearch,
                    onBookItemPressed=onBookItemPressed,
                    onBookmarkPressed=onBookmarkPressed,
                    navigationItemContent = navigationItemContentList,
                    currentPage=currentPage,
                    updatePage=updatePage,
                    modifier = modifier,
                    scrollState=scrollState
                )
            }
        }
    }
}

@Composable
private fun BookshelfAppContent(
    bookshelfUiState: BookshelfUiState,
    onTabPressed: (BookType) -> Unit,
    onSearch: (String) -> Unit,
    onBookItemPressed: (BookInfo) -> Unit,
    onBookmarkPressed:(Book)->Unit,
    navigationType: NavigationType,
    contentType: ContentType,
    navigationItemContent: List<NavigationItemContent>,
    currentPage:Int,
    updatePage: (Int) -> Unit,
    modifier:Modifier=Modifier,
    scrollState:LazyListState
){
    var input by remember{ mutableStateOf("android") }

    Box(modifier=modifier){
        Row(modifier=Modifier.fillMaxSize()){

            AnimatedVisibility(visible=navigationType==NavigationType.NAVIGATION_RAIL){
                BookNavigationRail(
                    currentTab = checkTabPressed(bookshelfUiState),
                    onTabPressed = onTabPressed,
                    navigationItemContentList = navigationItemContent
                )
            }

            Column(modifier=Modifier.fillMaxSize()) {
                if(contentType==ContentType.LIST_AND_DETAIL){
                    BookshelfListAndDetailContent(
                        books = checkBookList(bookshelfUiState).collectAsLazyPagingItems() ,
                        bookshelfUiState = bookshelfUiState,
                        onSearch=onSearch,
                        onBookItemPressed=onBookItemPressed,
                        input=input,
                        onInputChange = {input=it},
                        onInputReset = {input=it},
                        onBookmarkPressed=onBookmarkPressed,
                        currentPage=currentPage,
                        updatePage=updatePage,
                        scrollState = scrollState
                    )
                }else{
                    if(checkTabPressed(bookshelfUiState)==BookType.Bookmark
                        && checkBookmarkIsEmpty(bookshelfUiState)
                    ){
                        BookmarkEmptyScreen(modifier= Modifier
                            .fillMaxSize()
                            .weight(1f))
                    }else{
                        when(bookshelfUiState){
                            is BookshelfUiState.Success -> BookshelfListOnlyContent(
                                books=bookshelfUiState.list.book.collectAsLazyPagingItems(),
                                onSearch=onSearch,
                                onBookItemPressed=onBookItemPressed,
                                bookshelfUiState=bookshelfUiState,
                                input=input,
                                onInputChange = {input=it},
                                onInputReset = {input=it},
                                onBookmarkPressed=onBookmarkPressed,
                                currentPage=currentPage,
                                updatePage=updatePage,
                                scrollState = scrollState,
                                modifier=Modifier
                                    .padding(dimensionResource(R.dimen.list_only_content_column_padding))
                                    .fillMaxSize()
                                    .weight(1f),
                            )
                            is BookshelfUiState.Loading -> {
                                LoadingScreen(modifier= Modifier
                                    .fillMaxSize()
                                    .weight(1f))
                            }
                            is BookshelfUiState.Error -> {
                                ErrorScreen(
                                    retryAction = onSearch,
                                    input=input,
                                    modifier= Modifier
                                        .fillMaxSize()
                                        .weight(1f))
                            }
                        }
                    }
                }
                AnimatedVisibility(visible = navigationType==NavigationType.BOTTOM_NAVIGATION) {
                    BookBottomNavigationBar(
                        currentTab = checkTabPressed(bookshelfUiState),
                        onTabPressed = onTabPressed,
                        navigationItemContentList = navigationItemContent,
                        modifier=Modifier.fillMaxWidth()
                    )
                }
            }

        }
    }

}

@Composable
private fun LoadingScreen(modifier: Modifier=Modifier){
    Image(
        painter=painterResource(R.drawable.baseline_rotate_left_24),
        contentDescription=stringResource(R.string.loading),
        modifier=modifier.size(dimensionResource(R.dimen.loading_screen_image_size))
    )
}

@Composable
private fun ErrorScreen(
    retryAction:(String)->Unit,
    input:String,
    modifier: Modifier=Modifier
){
    Column(
        modifier=modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(R.drawable.baseline_error_outline_24),
            contentDescription = stringResource(R.string.error)
        )
        Text(
            text=stringResource(R.string.error),
            modifier=Modifier.padding(dimensionResource(R.dimen.error_screen_text_padding))
        )
        Button(onClick = {retryAction(input)}) {
            Text(text=stringResource(R.string.retry))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavigationDrawerContent(
    selectedTab:BookType,
    onTabPressed:(BookType)->Unit,
    navigationItemList:List<NavigationItemContent>,
    modifier:Modifier=Modifier
){
    Column(modifier=modifier) {
        for(naviItem in navigationItemList){
            NavigationDrawerItem(
                selected = selectedTab==naviItem.bookType,
                onClick = { onTabPressed(naviItem.bookType) },
                label = {
                    Text(
                        text=naviItem.text,
                        modifier=Modifier
                            .padding(start= dimensionResource(
                                R.dimen.permanent_navigation_drawer_content_text_padding)
                            )
                    )
                },
                icon={
                    Icon(
                        imageVector=naviItem.icon,
                        contentDescription=naviItem.text,
                        modifier=Modifier
                            .padding(start= dimensionResource(
                                R.dimen.permanent_navigation_drawer_content_icon_padding)
                            )
                    )
                }
            )
        }
    }
}

@Composable
private fun BookNavigationRail(
    currentTab:BookType,
    onTabPressed: (BookType) -> Unit,
    navigationItemContentList:List<NavigationItemContent>,
    modifier:Modifier=Modifier
){
    NavigationRail(modifier=modifier) {
        Column(
            modifier=Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            for(naviItem in navigationItemContentList){
                NavigationRailItem(
                    selected = currentTab== naviItem.bookType,
                    onClick = {onTabPressed(naviItem.bookType)},
                    icon={
                        Icon(imageVector = naviItem.icon,
                            contentDescription = naviItem.text
                        )
                    },
                    modifier=Modifier.padding(
                        dimensionResource(R.dimen.navigation_rail_item_padding)
                    )
                )
            }
        }
    }
}

@Composable
private fun BookBottomNavigationBar(
    currentTab: BookType,
    onTabPressed: (BookType) -> Unit,
    navigationItemContentList: List<NavigationItemContent>,
    modifier:Modifier=Modifier
){
    NavigationBar(modifier=modifier) {
        for(naviItem in navigationItemContentList){
            NavigationBarItem(
                selected = currentTab== naviItem.bookType,
                onClick = {onTabPressed(naviItem.bookType)},
                icon={
                    Icon(imageVector=naviItem.icon, contentDescription =naviItem.text)
                }
            )
        }
    }
}

private data class NavigationItemContent(
    val bookType: BookType,
    val icon: ImageVector,
    val text:String
)
