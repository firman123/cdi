package com.app.cdipoc.api

import android.content.Context
import com.app.cdipoc.extension.Constant
import com.app.cdipoc.extension.PrefManager

object ApiHeader {
    fun headerClient(context: Context): Map<String, String> {
        val appId = PrefManager.getString(context, Constant.HEADER.APP_ID, "").toString()
        val apiKey = PrefManager.getString(context, Constant.HEADER.API_KEY, "").toString()
        val headers = HashMap<String, String>()
        headers[Constant.HEADER.APP_ID] = appId
        headers[Constant.HEADER.API_KEY] = apiKey
        return headers
    }
}