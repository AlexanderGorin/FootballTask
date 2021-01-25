package com.alexandergorin.data

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

inline fun <reified T> createService(gson: Gson): T {

    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.ENDPOINT)
        .client(
            OkHttpClient.Builder()
                .apply {
                    if (BuildConfig.DEBUG) {
                        addInterceptor(
                            HttpLoggingInterceptor().apply {
                                level = HttpLoggingInterceptor.Level.BODY
                            }
                        )
                    }
                }
                .addInterceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                        .addHeader(TOKEN_HEADER_NAME, BuildConfig.TOKEN)
                        .build()
                    chain.proceed(newRequest)
                }
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    return retrofit.create(T::class.java)
}

const val TOKEN_HEADER_NAME = "X-Auth-Token"
