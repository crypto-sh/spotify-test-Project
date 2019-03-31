package com.spotifyplayer.api

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest


class MockServerDispatcher {

    /**
     * Return ok response from mock server
     */
    class RequestDispatcher : Dispatcher() {

        //TODO update fake response for check another service
        override fun dispatch(request: RecordedRequest?): MockResponse {

            if (request == null)
                return MockResponse().setResponseCode(500)

            return when {

                request.path == "api/token" -> MockResponse().setResponseCode(200).setBody("{data:FakeData}")// Authorization Result

                request.path == "api/search" -> MockResponse().setResponseCode(200).setBody("{codes:FakeCode}")// Page Result for show data

                else -> MockResponse().setResponseCode(404)

            }
        }
    }

    /**
     * Return error response from mock server
     */
    class ErrorDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest?): MockResponse {
            return MockResponse().setResponseCode(400)
        }
    }
}