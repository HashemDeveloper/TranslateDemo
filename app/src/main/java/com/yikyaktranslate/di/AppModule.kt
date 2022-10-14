package com.yikyaktranslate.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yikyaktranslate.data.RemoteRepo
import com.yikyaktranslate.service.face.TranslateAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRemoteRepo(translateAPI: TranslateAPI) = RemoteRepo(translateAPI)

    @Singleton
    @Provides
    fun provideTranslateAPI(): TranslateAPI {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder()
            .baseUrl("https://libretranslate.de")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TranslateAPI::class.java)
    }
}