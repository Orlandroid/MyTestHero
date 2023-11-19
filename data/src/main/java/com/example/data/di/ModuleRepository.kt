package com.example.data.di

import com.example.data.Repository
import com.example.data.db.EarthquakeDao
import com.example.domain.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

object ModuleRepository {

    @Singleton
    @Provides
    fun provideRepository(
        earthquakeDao: EarthquakeDao,
        remoteDataSource: RemoteDataSource
    ): Repository = Repository(earthquakeDao, remoteDataSource)
}