package com.compose.weatherapp.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object NewModule {

  /*  @Provides
    fun provideDatabase(@ApplicationContext app: Application): WeatherDatabase =
        Room.databaseBuilder(app, WeatherDatabase::class.java, Constants.DATABASE)
            .fallbackToDestructiveMigration()
            .build()*/
}