package com.dguardado19.pelisolms

import android.net.Uri
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*

object NetworkUtils {

    val MOVIES_API_BASE_URL = "http://www.omdbapi.com/"
    val TOKEN_API ="9a738add"

    private val TAG = NetworkUtils::class.java.simpleName

    fun buildUrl(movieName: String): URL {
        val builtUri= Uri.parse(MOVIES_API_BASE_URL)
            .buildUpon()
            .appendQueryParameter("apikey", TOKEN_API)
            .appendQueryParameter("t", movieName)
            .build()

        return try {
            URL(builtUri.toString())
        }catch (e: MalformedURLException){
            URL("")
        }

    }

    @Throws(IOException::class)
    fun getResponseFromHttpUrl(url: URL): String? {
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            val `in` = urlConnection.inputStream

            val scanner = Scanner(`in`)
            scanner.useDelimiter("\\A")

            val hasInput = scanner.hasNext()
            return if (hasInput) {
                scanner.next()
            } else {
                null
            }
        } finally {
            urlConnection.disconnect()
        }
    }
}