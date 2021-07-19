package com.example.movie_mvi_compose.data.di

import android.content.Context
import androidx.room.Room
import com.example.movie_mvi_compose.BuildConfig.DEBUG
import com.example.movie_mvi_compose.data.db.MovieDao
import com.example.movie_mvi_compose.data.db.MyDataBase
import com.example.movie_mvi_compose.data.network.Api.MovieClient
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().
        addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://raw.githubusercontent.com/android10/Sample-Data/master/Android-CleanArchitecture-Kotlin/")
            .build()

    }
     @Provides
     fun provideMovieApi(retrofit: Retrofit):MovieClient{
         return retrofit.create(MovieClient::class.java)
     }



    @Singleton
    @Provides
    fun provideMyDb(
        @ApplicationContext context: Context,
    ): MyDataBase {
      //  , callback: MyDataBase.Callback
        return Room
            .databaseBuilder(
                context,
                MyDataBase::class.java,
                "DATABASE_NAME"
            )
            .fallbackToDestructiveMigration()

//            .addCallback(callback)

            .build()
    }


    @Provides
    fun provideMyDAO(myDataBase: MyDataBase): MovieDao {
        return myDataBase.getMyDao()
    }



}