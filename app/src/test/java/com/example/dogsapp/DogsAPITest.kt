package com.example.dogsapp

import android.app.Application
import com.example.dogsapp.model.RandomDog
import com.example.dogsapp.network.DogAPI
import com.example.dogsapp.repository.DogRepository
import com.example.dogsapp.utils.Constants
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DogsAPITest {

    lateinit var mockWebServer: MockWebServer
    lateinit var apiService: DogAPI


    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder().baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create()).build().create(DogAPI::class.java)
    }


    @Test
    fun testgetEmptyRandomDog() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setBody("{}")
        mockWebServer.enqueue(mockResponse)
        val result = apiService.getRandomDog()
        mockWebServer.takeRequest()
        Assert.assertEquals(null, result.status)
    }


    @Test
    fun testdataRandomDog() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setBody(
            Constants.DUMMY_RANDOM_DOG_RESPONSE
        )
        mockWebServer.enqueue(mockResponse.setResponseCode(200))
        val result = apiService.getRandomDog()
        mockWebServer.takeRequest()

        Assert.assertEquals("success", result.status)
        Assert.assertEquals(
            "https://images.dog.ceo/breeds/schipperke/n02104365_9143.jpg",
            result.message
        )
    }


    @Test
    fun testEmptygetAllDogBreed() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setBody(
            "{}"
        )
        mockWebServer.enqueue(mockResponse.setResponseCode(200))
        val result = apiService.getAllDogsBreed()
        mockWebServer.takeRequest()
        Assert.assertEquals(null, result.status)
    }


    @Test
    fun testgetAllDogBreedData() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setBody(
            Constants.DUMMY_ALL_BREED_RESPONSE
        )
        mockWebServer.enqueue(mockResponse.setResponseCode(200))
        val result = apiService.getAllDogsBreed()
        mockWebServer.takeRequest()
        Assert.assertEquals("success", result.status)
        Assert.assertEquals("affenpinscher", result.message.get(0))
    }


    @After
    fun onStop() {
        mockWebServer.shutdown()
    }


}