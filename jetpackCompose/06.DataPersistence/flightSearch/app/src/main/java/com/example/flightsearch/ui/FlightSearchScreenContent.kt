package com.example.flightsearch.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.flightsearch.R
import com.example.flightsearch.data.model.Airport
import com.example.flightsearch.data.model.Bookmark
import com.example.flightsearch.viewmodel.airport.Item

@Composable
fun LoadingScreen(
    modifier:Modifier=Modifier
){
    Box(modifier=modifier){
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(
    modifier:Modifier=Modifier
){
    Box(modifier=modifier){
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = stringResource(R.string.error)
        )
    }
}

@Composable
fun EmptyScreen(
    modifier:Modifier=Modifier
){
    Box(modifier=modifier){
        Text(stringResource(R.string.empty))
    }
}

@Composable
fun SearchingScreen(
    searchQuery:String,
    onSearchQueryChange:(String)->Unit,
    items:List<Airport>,
    onSearch:(String)->Unit,
    modifier:Modifier=Modifier
){
    Column(modifier=modifier) {
        SearchField(
            query = searchQuery,
            onQueryChange = onSearchQueryChange,
            onSearch= {onSearch(searchQuery)}
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
    onInsert:(Bookmark)->Unit,
    onDelete:(Bookmark)->Unit,
    modifier:Modifier=Modifier
){
    Column(modifier=modifier) {
        SearchField(
            query = searchQuery,
            onQueryChange = onSearchQueryChange,
            onSearch= {onSearch(searchQuery)}
        )
        Text(listTitle)
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
    onInsert:(Bookmark)->Unit,
    onDelete:(Bookmark)->Unit,
    modifier:Modifier=Modifier
){
    Column(modifier=modifier) {
        SearchField(
            query = searchQuery,
            onQueryChange = onSearchQueryChange,
            onSearch= {onSearch(searchQuery)}
        )
        Text(listTitle)
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
    onSearch:(String)->Unit
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
        modifier= Modifier.fillMaxWidth()
    )
}

@Composable
fun FlightSearchAirportList(
    airport: Airport,
    item:Item,
    onInsert:(Bookmark)->Unit,
    onDelete:(Bookmark)->Unit
){
    var isBookmarked by remember{mutableStateOf(false)}
    Card{
        Row{
            Column {
                Text(text= stringResource(R.string.depart))
                Row{
                    Text(text=item.iataCode)
                    Text(text=item.name)
                }
                Text(text= stringResource(R.string.arrive))
                Row{
                    Text(text=airport.name)
                    Text(text=airport.iataCode)
                }
            }
            IconButton(onClick = {
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
    Card{
        Row{
            Column {
                Text(text= stringResource(R.string.depart))
                Text(text=bookmark.departureCode)
                Text(text= stringResource(R.string.arrive))
                Text(text=bookmark.destinationCode)
            }
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
    Row{
        Text(text=airport.iataCode+"\r")
        Text(text=airport.name)
    }
}