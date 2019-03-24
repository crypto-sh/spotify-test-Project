package com.spotifyplayer.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spotifyplayer.models.Artist


@Dao
interface ArtistDao {

    @Query("DELETE FROM artist")
    fun deleteAll()

    @Query("SELECT * FROM artist")
    fun loadAllArtist() : DataSource.Factory<Int,Artist>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(artists : List<Artist>)

    @Query("select * from artist where id = :Id")
    fun loadArtist(Id : String) : LiveData<Artist>
}