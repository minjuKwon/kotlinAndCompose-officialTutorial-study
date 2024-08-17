package com.example.flightsearch

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.flightsearch.data.database.AirportDao
import com.example.flightsearch.data.database.BookmarkDao
import com.example.flightsearch.data.database.FlightDatabase
import com.example.flightsearch.data.model.Bookmark
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ItemDaoTest{

    private lateinit var airportDao: AirportDao
    private lateinit var bookmarkDao:BookmarkDao
    private lateinit var flightDatabase: FlightDatabase

    private val bookmarkItem1 =Bookmark(1,"ABC","BCD")
    private val bookmarkItem2 = Bookmark(3,"CDE","DEF")

    @Before
    fun createDb(){
        val context: Context = ApplicationProvider.getApplicationContext()
        flightDatabase= Room.databaseBuilder(
            context, FlightDatabase::class.java,"test_database"
        ).createFromAsset("database/flight_search.db")
            .allowMainThreadQueries()
            .build()
        airportDao=flightDatabase.airportDao()
        bookmarkDao=flightDatabase.bookmarkDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        flightDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun airportDao_getAirport()= runBlocking {
        val item=airportDao.getAirport("ARN").first()
        assertEquals(item.id,2)
    }

    @Test
    @Throws(Exception::class)
    fun airportDao_getAirportsList()= runBlocking {
        val itemList=airportDao.getAirportsList("ARN").first()
        assertEquals(itemList.size,32)
        assertEquals(itemList[0].id,7)
    }

    @Test
    @Throws(Exception::class)
    fun airportDao_searchByKeyword()= runBlocking {
        val itemList1=airportDao.searchByKeyword("d").first()
        assertEquals(itemList1.size,10)
        val itemList2=airportDao.searchByKeyword("op").first()
        assertEquals(itemList2.size,4)
    }

    @Test
    @Throws(Exception::class)
    fun bookmark_returnAllItemsFromDB()= runBlocking {
        bookmarkDao.insertBookmark(bookmarkItem1)
        bookmarkDao.insertBookmark(bookmarkItem2)
        val itemList=bookmarkDao.getAllBookmark().first()
        assertEquals(itemList[0],bookmarkItem1)
        assertEquals(itemList[1],bookmarkItem2)
    }

    @Test
    @Throws(Exception::class)
    fun bookmarkDao_deleteItemIntoDB()= runBlocking {
        bookmarkDao.deleteBookmark(bookmarkItem1)
        var itemList=bookmarkDao.getAllBookmark().first()
        assertEquals(itemList.size,1)
        assertEquals(itemList[0],bookmarkItem2)

        bookmarkDao.deleteBookmark(bookmarkItem2)
        itemList=bookmarkDao.getAllBookmark().first()
        assertTrue(itemList.isEmpty())
    }

}