package com.example.dogsapp

import android.content.Context
import android.content.SharedPreferences
import com.example.dogsapp.utils.Constants
import com.example.dogsapp.utils.SharedPreferenceUtils
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class SharedPreferenceHelperTest {

    @Mock
    lateinit var mockSharedPreferenceUtils: SharedPreferenceUtils

    @Mock
    lateinit var mcontext: Context


    @Before
    fun initMocks() {
        MockitoAnnotations.openMocks(this)
        Mockito.`when`(
            mockSharedPreferenceUtils.setSharePrefrenceDataString(anyString(),anyString())
        ).thenReturn(true)


    }


    @Test
    fun testSetSharedPreference() {
        val sut =
            mockSharedPreferenceUtils.setSharePrefrenceDataString(Constants.FAVOURITE_DOG, "casper")
            Assert.assertEquals(true, sut)
    }


    @Test
    fun testGetSharedPreference() {
        val sut = mockSharedPreferenceUtils.getSharedPreferenceDataString(Constants.FAVOURITE_BREED)
        Assert.assertEquals(null, sut)
    }

}