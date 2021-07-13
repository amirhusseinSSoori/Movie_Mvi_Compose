package com.example.movie_mvi_compose.data.di

import android.graphics.Movie
import com.example.movie_mvi_compose.BuildConfig.DEBUG
import com.example.movie_mvi_compose.data.Api.MovieClient
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {



    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return loggingInterceptor
    }

    @Singleton
    @Provides
    fun provideOkHttp(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .readTimeout(500 , TimeUnit.SECONDS)
            .writeTimeout(500 , TimeUnit.SECONDS)
            .connectTimeout(500 , TimeUnit.SECONDS)
            .build()
    }
    @Singleton
    @Provides
    fun provideRetrofit(client: Lazy<OkHttpClient>): Retrofit {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("")
            .callFactory(object : Call.Factory{
                // this bellow fun ,called in background thread
                override fun newCall(request: Request): Call =
                    client.get().newCall(request)
            })
            .build()

    }
     @Provides
     fun provideMovieApi(retrofit: Retrofit):MovieClient{
         return retrofit.create(MovieClient::class.java)
     }



}