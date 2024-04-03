package com.cleanarchitectureexample.di

import android.content.Context
import androidx.room.Room
import com.cleanarchitectureexample.data.remote.ApiService
import com.cleanarchitectureexample.data.repository.PostRepositoryImplementation
import com.cleanarchitectureexample.database.AppDatabase
import com.cleanarchitectureexample.domain.repository.PostRepository
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
class AppModule {

    //will make variant
    @Provides
    fun provideBaseUrl(): String = "https://jsonplaceholder.typicode.com/"

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun providePostsRepository(apiService: ApiService, appDatabase: AppDatabase): PostRepository =
        PostRepositoryImplementation(apiService, appDatabase)

    @Provides
    @Singleton
    fun provideDatabaseObject(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "posts_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}