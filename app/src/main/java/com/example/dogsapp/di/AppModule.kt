package com.example.dogsapp.di

import android.app.Application
import android.content.Context
import com.example.dogsapp.DogsApplication
import com.example.dogsapp.network.DogAPI
import com.example.dogsapp.repository.DogRepository
import com.example.dogsapp.utils.Constants
import com.example.dogsapp.utils.SharedPreferenceUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule() {

    @Singleton
    @Provides
    fun provideDogsApi(): DogAPI {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create(DogAPI::class.java)
    }


    @Singleton
    @Provides
    fun provideDogsRepository(api: DogAPI, application: Application) =
        DogRepository(api, application)




    @Singleton
    @Provides
    fun provideSharedPreferenceUtils(application: Application) =
        SharedPreferenceUtils(context = application)


}