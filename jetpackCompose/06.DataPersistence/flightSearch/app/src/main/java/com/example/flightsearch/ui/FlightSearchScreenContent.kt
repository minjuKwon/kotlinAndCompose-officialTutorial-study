package com.example.flightsearch.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.flightsearch.R
import com.example.flightsearch.data.model.Airport
import com.example.flightsearch.data.model.Bookmark
import com.example.flightsearch.viewmodel.airport.Item

@Composable
fun LoadingScreen(
    modifier:Modifier=Modifier
){
    Box(
        modifier=modifier,
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(
    onHomeScreenChange:()->Unit,
    onSearchScreenChange:()->Unit,
    modifier:Modifier=Modifier
){
    Column(
        modifier=modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = stringResource(R.string.error),
            modifier= Modifier
                .size(
                    dimensionResource(R.dimen.error_screen_icon_size)
                )
                .padding(
                    bottom = dimensionResource(R.dimen.error_screen_icon_padding_bottom)
                )
        )
        Button(
            onClick = onHomeScreenChange,
            modifier=Modifier
                .padding(
                    bottom=dimensionResource(R.dimen.error_screen_button_padding_bottom)
                )
        ) {
            Text(stringResource(R.string.back_to_home))
        }
        Button(onClick = onSearchScreenChange) {
            Text(stringResource(R.string.back_to_search))
        }
    }
}

@Composable
fun EmptyScreen(
    modifier:Modifier=Modifier
){
    Box(
        modifier=modifier,
        contentAlignment = Alignment.TopCenter
    ){
        Text(
            stringResource(R.string.empty),
            modifier=Modifier
                .padding(top=dimensionResource(R.dimen.empty_screen_text_padding_top))
        )
    }
}

@Composable
fun SearchingScreen(
    searchQuery:String,
    onSearchQueryChange:(String)->Unit,
    items:List<Airport>,
    onSearch:(String)->Unit,
    onReset: (String) -> Unit,
    modifier:Modifier=Modifier
){
    Column(
        modifier=modifier,
        horizontalAlignment=Alignment.CenterHorizontally
    ) {
        SearchField(
            query = searchQuery,
            onQueryChange = onSearchQueryChange,
            onSearch= onSearch,
            onReset = onReset
        )
        LazyColumn{
            items(items,key={it.id}){
                FlightSearchRecommendItem(it)
            }
        }
    }
}

@Composable
fun SearchResultScreen(
    searchQuery:String,
    onSearchQueryChange:(String)->Unit,
    listTitle:String,
    items:List<Airport>,
    item:Item,
    onSearch:(String)->Unit,
    onReset: (String) -> Unit,
    onInsert:(Bookmark)->Unit,
    onDelete:(Bookmark)->Unit,
    modifier:Modifier=Modifier
){
    Column(
        modifier=modifier,
        horizontalAlignment=Alignment.CenterHorizontally
    ) {
        SearchField(
            query = searchQuery,
            onQueryChange = onSearchQueryChange,
            onSearch= onSearch,
            onReset = onReset
        )
        Text(
            text=listTitle,
            modifier= Modifier
                .align(Alignment.Start)
                .padding(dimensionResource(R.dimen.search_result_screen_title_text_padding))
        )
        LazyColumn{
            items(items,key={it.id}){
                FlightSearchAirportList(
                    airport=it,
                    item=item,
                    onInsert=onInsert,
                    onDelete=onDelete
                )
            }
        }
    }
}

@Composable
fun BookmarkScreen(
    searchQuery:String,
    onSearchQueryChange:(String)->Unit,
    listTitle:String,
    items:List<Bookmark>?,
    onSearch:(String)->Unit,
    onReset: (String) -> Unit,
    onInsert:(Bookmark)->Unit,
    onDelete:(Bookmark)->Unit,
    modifier:Modifier=Modifier
){
    Column(
        modifier=modifier,
        horizontalAlignment=Alignment.CenterHorizontally
    ) {
        SearchField(
            query = searchQuery,
            onQueryChange = onSearchQueryChange,
            onSearch= onSearch,
            onReset = onReset
        )
        Text(
            text=listTitle,
            modifier= Modifier
                .align(Alignment.Start)
                .padding(dimensionResource(R.dimen.bookmark_screen_title_text_padding))
        )
        if(items?.isEmpty()==false){
            LazyColumn{
                items(items=items,key={it.id}){
                    FlightSearchBookmarkList(
                        bookmark=it,
                        onInsert=onInsert,
                        onDelete=onDelete
                    )
                }
            }
        }else{
            EmptyScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
    query:String,
    onQueryChange:(String)->Unit,
    onSearch:(String)->Unit,
    onReset:(String)->Unit
){
    TextField(
        value = query,
        onValueChange = onQueryChange,
        label={Text(stringResource(R.string.search_label))},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search),
                modifier=Modifier.clickable{onSearch(query)}
            )
        },
        trailingIcon={
            Icon(
                imageVector=Icons.Default.Close,
                contentDescription = stringResource(R.string.keyword_reset),
                modifier=Modifier.clickable{onReset("")}

            )
        },
        keyboardOptions=KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions= KeyboardActions(
            onSearch = {onSearch(query)},
        ),
        modifier= Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.search_field_padding))
    )
}

@Composable
fun FlightSearchAirportList(
    airport: Airport,
    item:Item,
    onInsert:(Bookmark)->Unit,
    onDelete:(Bookmark)->Unit
){
    var isBookmarked by rememberSaveable{mutableStateOf(false)}
    Card(
        modifier= Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(R.dimen.airport_list_item_padding_horizontal),
                vertical = dimensionResource(R.dimen.airport_list_item_padding_vertical)
            )
    ){
        Row(
            modifier= Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.airport_list_item_content_padding)),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column {
                Text(text= stringResource(R.string.depart))
                Row(modifier=Modifier
                    .padding(top= dimensionResource(R.dimen.airport_list_item_content_text_padding_top))
                ){
                    Text(text=item.iataCode)
                    Text(
                        text=item.name,
                        modifier=Modifier.padding(start= dimensionResource(R.dimen.airport_list_item_content_text_padding_start))
                    )
                }
                Text(
                    text= stringResource(R.string.arrive),
                    modifier=Modifier
                        .padding(top= dimensionResource(R.dimen.airport_list_item_content_text_padding_top))
                )
                Row(
                    modifier=Modifier
                        .padding(top= dimensionResource(R.dimen.airport_list_item_content_text_padding_top))
                ){
                    Text(text=airport.name)
                    Text(
                        text=airport.iataCode,
                        modifier=Modifier
                            .padding(start= dimensionResource(R.dimen.airport_list_item_content_text_padding_start))
                    )
                }
            }
            Spacer(modifier=Modifier.weight(1f))
            IconButton(
                onClick = {
                isBookmarked = if(isBookmarked){
                    onDelete(
                        Bookmark(
                            departureCode=item.iataCode,
                            destinationCode=airport.iataCode
                        )
                    )
                    false
                } else {
                    onInsert(
                        Bookmark(
                            departureCode=item.iataCode,
                            destinationCode=airport.iataCode
                        )
                    )
                    true
                }
            }) {
                Icon(
                    imageVector = if(isBookmarked){Icons.Default.Bookmark}
                    else {Icons.Default.BookmarkBorder},
                    contentDescription = stringResource(R.string.bookmark)
                )
            }
        }
    }
}

@Composable
fun FlightSearchBookmarkList(
    bookmark: Bookmark,
    onInsert:(Bookmark)->Unit,
    onDelete:(Bookmark)->Unit
){
    var isBookmarked by remember{mutableStateOf(true)}
    Card(
        modifier= Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(R.dimen.bookmark_list_item_padding_horizontal),
                vertical = dimensionResource(R.dimen.bookmark_list_item_padding_vertical)
            )
    ){
        Row(
            modifier= Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.bookmark_list_item_content_padding)),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    dimensionResource(R.dimen.bookmark_list_item_content_text_padding_top)
                )
            ) {
                Text(text= stringResource(R.string.depart))
                Text(text=bookmark.departureCode)
                Text(text= stringResource(R.string.arrive))
                Text(text=bookmark.destinationCode)
            }
            Spacer(modifier=Modifier.weight(1f))
            IconButton(onClick = {
                isBookmarked = if(isBookmarked){
                    onDelete(bookmark)
                    false
                } else {
                    onInsert(bookmark)
                    true
                }
            }) {
                Icon(
                    imageVector = if(isBookmarked){Icons.Default.Bookmark}
                    else {Icons.Default.BookmarkBorder},
                    contentDescription = stringResource(R.string.bookmark)
                )
            }
        }
    }
}

@Composable
fun FlightSearchRecommendItem(
    airport: Airport
){
    Row(
        modifier=Modifier
            .padding(top=dimensionResource(R.dimen.searching_recommend_item_padding_top)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Text(
            text=airport.iataCode,
            modifier=Modifier
                .padding(end=dimensionResource(R.dimen.searching_recommend_item_text_padding_end))
        )
        Text(text=airport.name)
    }
}