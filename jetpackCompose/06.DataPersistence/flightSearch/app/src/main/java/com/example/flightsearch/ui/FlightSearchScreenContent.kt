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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.example.flightsearch.R
import com.example.flightsearch.data.model.Airport
import com.example.flightsearch.data.model.Bookmark
import com.example.flightsearch.ui.theme.Brown
import com.example.flightsearch.ui.theme.SkyBlue
import com.example.flightsearch.viewmodel.airport.INVALID_QUERY
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
    mode:Int,
    modifier:Modifier=Modifier
){
    Box(
        modifier=modifier,
        contentAlignment = Alignment.TopCenter
    ){
        Text(
            text=
            if(mode==INVALID_QUERY) stringResource(R.string.invalid_query)
            else stringResource(R.string.empty),
            modifier=Modifier
                .padding(top=dimensionResource(R.dimen.empty_screen_text_padding_top))
        )
    }
}

@Composable
fun SearchingScreen(
    items:List<Airport>,
    textFieldParams: TextFieldParams,
    screenModifier:Modifier=Modifier,
    textFieldModifier:Modifier=Modifier
){
    Column(
        modifier=screenModifier,
        horizontalAlignment=Alignment.CenterHorizontally
    ) {
        SearchField(
            textFieldParams=textFieldParams,
            modifier=textFieldModifier
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
    item:Item,
    items:List<Airport>,
    listTitle:String,
    textFieldParams: TextFieldParams,
    databaseDaoParams: DatabaseDaoParams,
    modifier:Modifier=Modifier
){
    Column(
        modifier=modifier,
        horizontalAlignment=Alignment.CenterHorizontally
    ) {
        SearchField(
            textFieldParams = textFieldParams
        )
        Text(
            text=listTitle,
            fontWeight=FontWeight.Bold,
            modifier= Modifier
                .align(Alignment.Start)
                .padding(dimensionResource(R.dimen.search_result_screen_title_text_padding))
        )
        LazyColumn{
            items(items,key={it.id}){
                FlightSearchAirportList(
                    airport=it,
                    item=item,
                    databaseDaoParams = databaseDaoParams
                )
            }
        }
    }
}

@Composable
fun BookmarkScreen(
    items:List<Bookmark>?,
    listTitle:String,
    emptyScreenMode:Int,
    textFieldParams: TextFieldParams,
    databaseDaoParams: DatabaseDaoParams,
    modifier:Modifier=Modifier
){
    Column(
        modifier=modifier,
        horizontalAlignment=Alignment.CenterHorizontally
    ) {
        SearchField(
            textFieldParams = textFieldParams
        )
        Text(
            text=listTitle,
            fontWeight=FontWeight.Bold,
            modifier= Modifier
                .align(Alignment.Start)
                .padding(dimensionResource(R.dimen.bookmark_screen_title_text_padding))
        )
        if(items?.isEmpty()==false){
            LazyColumn{
                items(items=items,key={it.id}){
                    FlightSearchBookmarkList(
                        bookmark=it,
                        databaseDaoParams=databaseDaoParams
                    )
                }
            }
        }else{
            EmptyScreen(emptyScreenMode)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
    textFieldParams: TextFieldParams,
    modifier:Modifier=Modifier
){
    //onReset 올바른 타이밍 적용하기 위한 플래그
    //onValueChange 새로운 값이 리셋된 값으로 적용되지 않는 문제 방지
    var isResetting by remember{ mutableStateOf(true) }
    TextField(
        value = textFieldParams.query,
        onValueChange = {
            if(isResetting) textFieldParams.onQueryChange(it)
            else textFieldParams.onReset(TextFieldValue(""))
        },
        label={Text(stringResource(R.string.search_label))},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search),
                modifier=Modifier.clickable{
                    textFieldParams.onSearch(textFieldParams.query.text)
                }
            )
        },
        trailingIcon={
            Icon(
                imageVector=Icons.Default.Close,
                contentDescription = stringResource(R.string.keyword_reset),
                modifier=Modifier.clickable{
                    isResetting=false
                    textFieldParams.onReset(TextFieldValue(""))
                }

            )
        },
        keyboardOptions=KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions= KeyboardActions(
            onSearch = {textFieldParams.onSearch(textFieldParams.query.text)},
        ),
        modifier= modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.search_field_padding)),
        colors=TextFieldDefaults.textFieldColors(
            containerColor = SkyBlue
        )
    )
    isResetting=true
}

@Composable
fun FlightSearchAirportList(
    item:Item,
    airport: Airport,
    databaseDaoParams: DatabaseDaoParams
){
    var isBookmarked by rememberSaveable{mutableStateOf(false)}
    Card(
        modifier= Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(R.dimen.airport_list_item_padding_horizontal),
                vertical = dimensionResource(R.dimen.airport_list_item_padding_vertical)
            ),
        colors=CardDefaults.cardColors(Color.LightGray)
    ){
        Row(
            modifier= Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.airport_list_item_content_padding)),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(modifier=Modifier.fillMaxWidth(0.8f)) {
                Text(text= stringResource(R.string.depart))
                Row(modifier=Modifier
                    .padding(top= dimensionResource(R.dimen.airport_list_item_content_text_padding_top))
                ){
                    Text(text=item.iataCode,fontWeight=FontWeight.Bold)
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
                    Text(text=airport.iataCode,fontWeight=FontWeight.Bold)
                    Text(
                        text=airport.name,
                        modifier=Modifier
                            .padding(start= dimensionResource(R.dimen.airport_list_item_content_text_padding_start))
                    )
                }
            }
            IconButton(
                onClick = {
                isBookmarked = if(isBookmarked){
                    databaseDaoParams.onDelete(
                        Bookmark(
                            departureCode=item.iataCode,
                            destinationCode=airport.iataCode
                        )
                    )
                    false
                } else {
                    databaseDaoParams.onInsert(
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
                    else {Icons.Default.Bookmark},
                    contentDescription = stringResource(R.string.bookmark),
                    tint=if(isBookmarked)Brown else Color.DarkGray
                )
            }
        }
    }
}

@Composable
fun FlightSearchBookmarkList(
    bookmark: Bookmark,
    databaseDaoParams: DatabaseDaoParams
){
    var isBookmarked by remember{mutableStateOf(true)}
    Card(
        modifier= Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(R.dimen.bookmark_list_item_padding_horizontal),
                vertical = dimensionResource(R.dimen.bookmark_list_item_padding_vertical)
            ),
        colors=CardDefaults.cardColors(Color.LightGray)
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
                Text(text=bookmark.departureCode,fontWeight=FontWeight.Bold)
                Text(text= stringResource(R.string.arrive))
                Text(text=bookmark.destinationCode,fontWeight=FontWeight.Bold)
            }
            Spacer(modifier=Modifier.weight(1f))
            IconButton(onClick = {
                isBookmarked = if(isBookmarked){
                    databaseDaoParams.onDelete(bookmark)
                    false
                } else {
                    databaseDaoParams.onInsert(bookmark)
                    true
                }
            }) {
                Icon(
                    imageVector = if(isBookmarked){Icons.Default.Bookmark}
                    else {Icons.Default.Bookmark},
                    contentDescription = stringResource(R.string.bookmark),
                    tint=if(isBookmarked)Brown else Color.DarkGray
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
        modifier= Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(R.dimen.searching_recommend_item_padding_horizontal),
                vertical = dimensionResource(R.dimen.searching_recommend_item_padding_vertical)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ){
        Text(
            text=airport.iataCode,
            fontWeight=FontWeight.Bold,
            modifier=Modifier
                .padding(end=dimensionResource(R.dimen.searching_recommend_item_text_padding_end))
        )
        Text(text=airport.name)
    }
}