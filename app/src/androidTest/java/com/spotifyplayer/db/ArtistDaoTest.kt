package com.spotifyplayer.db


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.spotifyplayer.LiveDataTestUtil
import org.junit.*

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ArtistDaoTest {

    @Rule
    @JvmField
    var instant = InstantTaskExecutorRule()

    var database   : SpotifyDb?   = null
    var artistDao  : ArtistDao?   = null


    @Before
    fun initDatabase() {
        database    = SpotifyDb.getInstance(InstrumentationRegistry.getInstrumentation().context,true)
        artistDao   = database!!.artistDao()
    }

    @After
    fun closeDatabase(){
        database!!.close()
    }

    @Test
    fun step1_getArtistWithoutId(){
        artistDao!!.deleteAll()
        val items =  LiveDataTestUtil.getValue(artistDao!!.loadAllArtist())
        Assert.assertTrue(items.isEmpty())
    }

    @Test
    fun step2_getArtistAfterInsert(){
        artistDao!!.insertAll(artists = TestData.artist)
        val items =  LiveDataTestUtil.getValue(artistDao!!.loadAllArtist())
        Assert.assertTrue(items.size == TestData.artist.size)
    }

    @Test
    fun step3_getArtistById(){
        val artist            =  LiveDataTestUtil.getValue(artistDao!!.loadArtist(TestData.artist_one.id))
        Assert.assertEquals(artist.id             , TestData.artist_one.id)
        Assert.assertEquals(artist.href           , TestData.artist_one.href)
        Assert.assertEquals(artist.name           , TestData.artist_one.name)

        Assert.assertEquals(artist.images         , TestData.artist_one.images)

        Assert.assertEquals(artist.popularity     , TestData.artist_one.popularity)
        Assert.assertEquals(artist.spotify        , TestData.artist_one.spotify)
        Assert.assertEquals(artist.type           , TestData.artist_one.type)
        Assert.assertEquals(artist.uri            , TestData.artist_one.uri)
    }


}