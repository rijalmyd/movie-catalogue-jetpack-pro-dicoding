package com.rijaldev.inmovies.di

import android.content.Context
import androidx.room.Room
import com.rijaldev.inmovies.BuildConfig
import com.rijaldev.inmovies.data.source.MovieCatalogueRepository
import com.rijaldev.inmovies.data.source.local.LocalDataSource
import com.rijaldev.inmovies.data.source.local.room.MovieCatalogueDatabase
import com.rijaldev.inmovies.data.source.remote.RemoteDataSource
import com.rijaldev.inmovies.data.source.remote.response.api.ApiService
import com.rijaldev.inmovies.utils.AppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource,
        appExecutors: AppExecutors
    ): MovieCatalogueRepository =
        MovieCatalogueRepository(remoteDataSource, localDataSource, appExecutors)

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource =
        RemoteDataSource(apiService)

    @Provides
    @Singleton
    fun provideLocalDataSource(database: MovieCatalogueDatabase): LocalDataSource =
        LocalDataSource(database.movieCatalogueDao())

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MovieCatalogueDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            MovieCatalogueDatabase::class.java,
            "movie.db"
        ).build()

    @Provides
    @Singleton
    fun provideAppExecutors(): AppExecutors = AppExecutors()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}