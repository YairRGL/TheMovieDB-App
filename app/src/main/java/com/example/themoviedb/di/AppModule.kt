package com.example.themoviedb.di

import android.content.Context
import androidx.room.Room
import com.example.themoviedb.data.MovieDao
import com.example.themoviedb.data.MovieDatabase
import com.example.themoviedb.network.TheMovieDBApi
import com.example.themoviedb.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao = movieDatabase.movieDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): MovieDatabase =
        Room.databaseBuilder(context, MovieDatabase::class.java, "movie_database").fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun clientInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request()
        val newUrl = request.url.newBuilder()
            .addQueryParameter("api_key", Constants.API_KEY)
            .build()

        val newRequest = request.newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    @Provides
    @Singleton
    fun provideTMDBApi(): TheMovieDBApi {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(clientInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.TMDBAPI_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheMovieDBApi::class.java)
    }
}