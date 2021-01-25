package com.alexandergorin.footballtask.di

import android.content.Context
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.alexandergorin.data.api.FootballApi
import com.alexandergorin.data.createService
import com.alexandergorin.data.utils.LocalDateTimeTypeAdapter
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import org.threeten.bp.LocalDateTime
import javax.inject.Singleton

@Module
class AppServiceModule {

    @Provides
    @Singleton
    fun provideGson(): Gson =
        Gson()
            .newBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeTypeAdapter())
            .create()

    @Provides
    @Singleton
    fun provideFootballApi(gson: Gson): FootballApi = createService(gson)

    @Provides
    @Singleton
    fun provideSVGImageLoader(context: Context): ImageLoader = ImageLoader.Builder(context)
        .componentRegistry { add(SvgDecoder(context)) }
        .build()
}
