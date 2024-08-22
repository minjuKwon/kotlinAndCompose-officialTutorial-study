package com.example.flightsearch

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

private const val SEARCH_PREFERENCE_NAME="search_preferences"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name= SEARCH_PREFERENCE_NAME
)