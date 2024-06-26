package com.example.bookshelf.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bookshelf.network.Book

private const val STARTING_KEY=0

class BookPagingSource(
    private val bookshelfRepository: BookshelfRepository,
    private val input:String
): PagingSource<Int, Book>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        return try{
            val position = params.key?:STARTING_KEY
            val offset = position * params.loadSize
            val data=bookshelfRepository
                .getBookListInformation(
                    query=input,
                    count=params.loadSize,
                    startIndex = offset
            ).book
            LoadResult.Page(
                data=data,
                prevKey = if(position==STARTING_KEY) null else position-1,
                nextKey = if(data.isEmpty()) null else position+1
            )
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition?.let { anchorPosition->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?:state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}