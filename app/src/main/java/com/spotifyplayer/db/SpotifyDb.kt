package com.spotifyplayer.db

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.spotifyplayer.models.Artist


@Database(entities = arrayOf(Artist::class),version = 1,exportSchema = false)
@TypeConverters(DataConvertor::class)
abstract class SpotifyDb : RoomDatabase(){

    abstract fun artistDao() : ArtistDao

    companion object {

        @VisibleForTesting
        val DATABASE_NAME = "spotify.db"

        @Volatile
        private var INSTANCE : SpotifyDb? = null

        fun getInstance(context: Context, testMode: Boolean): SpotifyDb =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context, testMode).also {
                    INSTANCE = it
                }
            }

        private fun buildDatabase(context: Context, testMode: Boolean): SpotifyDb {
            return if (testMode) {
                Room
                    .inMemoryDatabaseBuilder(context.applicationContext, SpotifyDb::class.java)
                    .allowMainThreadQueries()
                    .build()
            } else {
                Room.databaseBuilder(context.applicationContext, SpotifyDb::class.java, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build()
            }
        }
    }

}