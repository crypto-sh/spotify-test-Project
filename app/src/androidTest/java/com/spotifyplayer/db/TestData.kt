package com.spotifyplayer.db


import com.spotifyplayer.models.Artist
import com.spotifyplayer.models.Image

class TestData {

    companion object {

        private val image_1  = Image(100,100,"")
        private val image_2  = Image(100,100,"")
        val artist_one = Artist("_id_one","testName1","artist","artist url","href", arrayOf(image_1, image_2).toList(),10,"string spotify")
        val artist_two = Artist("_id_two","testName2","artist","artist url","href", arrayOf(image_1, image_2).toList(),10,"string spotify")
        val artist = arrayOf(artist_one, artist_two).toList()

    }

}