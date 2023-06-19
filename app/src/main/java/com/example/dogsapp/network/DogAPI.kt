package com.example.dogsapp.network

import com.example.dogsapp.model.DogBreed
import com.example.dogsapp.model.DogBreedList
import com.example.dogsapp.model.RandomDog
import retrofit2.http.GET
import retrofit2.http.Path

interface DogAPI {


    @GET("breeds/list/")
    suspend fun getAllDogsBreed(): DogBreedList

    @GET("breed/{breed}/images")
    suspend fun getDogsBreed(@Path("breed") breedName: String): DogBreed

    @GET("breeds/image/random")
    suspend fun getRandomDog(): RandomDog

}