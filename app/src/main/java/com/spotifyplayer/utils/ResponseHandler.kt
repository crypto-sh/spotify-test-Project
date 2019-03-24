package com.spotifyplayer.utils

import com.github.library.response.ResponseJsonHandler
import org.json.JSONArray
import org.json.JSONObject

abstract class ResponseHandler : ResponseJsonHandler() {

    override fun onSuccess(result: JSONObject?) {
        onSuccessReponse("$result")
    }

    override fun onSuccess(result: JSONArray?) {
        onSuccessReponse("$result")
    }

    override fun onSuccess(result: String?) {
        onSuccessReponse(result)
    }

    override fun onFailure(errorCode: Int, errorMsg: String?) {
        onFailureResponse(errorMsg)
    }

    abstract fun onSuccessReponse(result: String?)
    abstract fun onFailureResponse(errorMsg: String?)

}