package com.example.chat.remote.service

import com.google.gson.Gson
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceFactory {

    private const val BASE_URL = "http://n964182b.bget.ru/rest_api/"

    private const val CONNECT_TIMEOUT_MILLIS = 5_000L
    private const val READ_TIMEOUT_MILLIS = 5_000L

    fun makeService(isDebug: Boolean): ApiService {
        val okHttpClient = makeOkHttpClient(
            makeLoggingInterceptor(isDebug)
        )
        return makeService(okHttpClient, Gson())
    }

    private fun makeService(okHttpClient: OkHttpClient, gson: Gson): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiService::class.java)
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            .readTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            .build()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }
}