package com.example.dogsapp.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class SharedPreferenceUtils @Inject constructor(val context: Context) {


    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)

    fun getSharedPreferenceDataString(key: String): String? {
        val data = sharedPreferences.getString(key, "null")
        return data

    }

    fun setSharePrefrenceDataString(key: String, value: String): Boolean {
        try {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(key, value)
            editor.apply()
            return true
        } catch (e: Exception) {
            return false
        }

    }
//    fun getSharedPreferenceDataBoolean(key: String): Boolean? {
//        val data = sharedPreferences.getBoolean(key, false)
//        return data
//
//    }
//
//    fun setSharedPreferenceDataBoolean(key: String, value: Boolean) {
//        val data = sharedPreferences.getString(key, "null")
//
//
//    }

}