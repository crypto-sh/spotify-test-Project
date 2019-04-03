package com.spotifyplayer.api


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.spotifyplayer.R
import com.spotifyplayer.ui.ListWithoutPaging
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class webServiceTest  {

    @get:Rule
    val activityRule = ActivityTestRule(ListWithoutPaging::class.java)

    var webServer : MockWebServer? = null

    @Before
    fun setup() {
        webServer = MockWebServer()
        webServer!!.start(8080)
    }

    @Test
    fun testMockWebService() {
        webServer!!.setDispatcher(MockServerDispatcher.RequestDispatcher())
        val intent              = ListWithoutPaging.intentFor(
            context = InstrumentationRegistry.getInstrumentation().targetContext,
            isTestMode = false
        )
        activityRule.launchActivity(intent)
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
    }
    //TODO add more test for mocking webService


    @After
    fun tearDown() {
        webServer!!.shutdown()
    }



}