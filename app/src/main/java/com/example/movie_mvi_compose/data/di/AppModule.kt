package com.example.movie_mvi_compose.data.di

import android.app.Application
import android.content.Context
import com.comexample.moviemvicompose.MovieEntityQueries
import com.example.movie_mvi_compose.BuildConfig.DEBUG
import com.example.movie_mvi_compose.Database
import com.example.movie_mvi_compose.data.network.Api.MovieClient
import com.example.movie_mvi_compose.data.repository.details.DetailsRepository
import com.example.movie_mvi_compose.data.repository.details.DetailsRepositoryImp
import com.example.movie_mvi_compose.data.repository.movie.MovieRepository
import com.example.movie_mvi_compose.data.repository.movie.MovieRepositoryImp
import com.example.movie_mvi_compose.data.source.LocalSource
import com.example.movie_mvi_compose.data.source.RemoteSource
import com.google.gson.GsonBuilder
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
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
        loggingInterceptor.level =
            if (DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return loggingInterceptor
    }

    @Singleton
    @Provides
    fun provideOkHttp(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .readTimeout(500, TimeUnit.SECONDS)
            .writeTimeout(500, TimeUnit.SECONDS)
            .connectTimeout(10000, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .create()
            )
        )
            .baseUrl("https://raw.githubusercontent.com/android10/Sample-Data/master/Android-CleanArchitecture-Kotlin/")
            .build()

    }



    @Provides
    fun provideMovieApi(retrofit: Retrofit): MovieClient {
        return retrofit.create(MovieClient::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application
    ): Database = app.applicationContext.createDatabaseDriver()
        .run {
            Database(this)
        }


    @Provides
    @Singleton
    fun provideMoviesQueries(
        db: Database
    ): MovieEntityQueries = db.movieEntityQueries



    @Provides
    fun provideMovieRepository(
        network: RemoteSource,
        localSource: LocalSource,
    ): MovieRepository {
        return MovieRepositoryImp(network, localSource)
    }

    @Provides
    fun provideDetailsRepository(network: RemoteSource): DetailsRepository {
        return DetailsRepositoryImp(network)
    }


}

fun Context.createDatabaseDriver() = AndroidSqliteDriver(
    schema = Database.Schema,
    context = this,
    name = "DATABASE_NAME",
)