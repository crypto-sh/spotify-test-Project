package com.spotifyplayer.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spotifyplayer.models.Artist


@Dao
interface ArtistDao {

    @Query("DELETE FROM artists")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(artists : List<Artist>)


    @Query("SELECT * FROM artists")
    fun loadAllArtist() : LiveData<List<Artist>>

    @Query("select * from artists where id = :Id")
    fun loadArtist(Id : String) : LiveData<Artist>
}