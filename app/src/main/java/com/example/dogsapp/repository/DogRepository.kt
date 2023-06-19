package com.example.dogsapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dogsapp.model.DogBreed
import com.example.dogsapp.model.DogBreedList
import com.example.dogsapp.model.RandomDog
import com.example.dogsapp.network.DogAPI
import com.example.dogsapp.utils.InternetUtils
import com.example.dogsapp.utils.Response
import javax.inject.Inject

class DogRepository @Inject constructor(val dogAPI: DogAPI, val context: Context) {

    private val mutableLiveDataDogBreedList = MutableLiveData<Response<DogBreedList>>()
    val liveDataDogBreedList: LiveData<Response<DogBreedList>>
        get() = mutableLiveDataDogBreedList


    private val mutableLiveDataDogBreed = MutableLiveData<Response<DogBreed>>()
    val liveDataDogBreed: LiveData<Response<DogBreed>>
        get() = mutableLiveDataDogBreed

    private val mutableLiveDataRandom = MutableLiveData<Response<RandomDog>>()
    val liveDataRandom: LiveData<Response<RandomDog>>
        get() = mutableLiveDataRandom

    suspend fun getDogBreedList() {
        if (InternetUtils.isInternetAvailable(context)) {
            try {
                mutableLiveDataDogBreedList.postValue(Response.Loading(true))
                val result = dogAPI.getAllDogsBreed()
                if (result.message.isNotEmpty() && result.status == "success") {
                    mutableLiveDataDogBreedList.postValue(Response.Loading(false))
                    mutableLiveDataDogBreedList.postValue(Response.Success(result))
                } else {
                    mutableLiveDataDogBreedList.postValue(Response.Error(result.status))
                }

            } catch (e: Exception) {
                mutableLiveDataDogBreedList.postValue(Response.Error(e.message))

            }

        } else {
            mutableLiveDataDogBreedList.postValue(Response.Loading(false))
            mutableLiveDataDogBreedList.postValue(Response.Error("No active internet available"))


        }
    }

    suspend fun getDogBreed(breedname: String) {


        if (InternetUtils.isInternetAvailable(context)) {
            try {
                mutableLiveDataDogBreed.postValue(Response.Loading(true))
                val result = dogAPI.getDogsBreed(breedname)
                if (result.message.isNotEmpty() && result.status == "success") {

                    mutableLiveDataDogBreedList.postValue(Response.Loading(false))
                    mutableLiveDataDogBreed.postValue(Response.Success(result))
                } else {
                    mutableLiveDataDogBreedList.postValue(Response.Loading(false))
                    mutableLiveDataDogBreedList.postValue(Response.Error(result.status))

                }

            } catch (e: Exception) {
                mutableLiveDataDogBreedList.postValue(Response.Loading(false))
                mutableLiveDataDogBreedList.postValue(Response.Error(e.message))

            }
        } else {
            mutableLiveDataDogBreedList.postValue(Response.Loading(false))
            mutableLiveDataDogBreedList.postValue(Response.Error("No active internet available"))


        }

    }

    suspend fun getRandomDog() {
        if (InternetUtils.isInternetAvailable(context)) {

            try {
                mutableLiveDataRandom.postValue(Response.Loading(true))
                val result = dogAPI.getRandomDog()
                if (result.message.isNotEmpty() && result.status == "success") {

                    mutableLiveDataRandom.postValue(Response.Loading(false))
                    mutableLiveDataRandom.postValue(Response.Success(result))
                }

            } catch (e: java.lang.Exception) {
                mutableLiveDataRandom.postValue(Response.Loading(false))
                mutableLiveDataRandom.postValue(Response.Error(e.message.toString()))
            }

        } else {
            mutableLiveDataRandom.postValue(Response.Loading(false))
            mutableLiveDataRandom.postValue(Response.Error("No active internet available"))

        }
    }

}