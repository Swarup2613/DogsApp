package com.example.dogsapp.viewModel

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogsapp.model.DogBreed
import com.example.dogsapp.repository.DogRepository
import com.example.dogsapp.utils.Helper
import com.example.dogsapp.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogBreedViewModel @Inject constructor(val repository: DogRepository) : ViewModel() {

    val liveDataDogBreed: LiveData<Response<DogBreed>>
        get() = repository.liveDataDogBreed



    fun getDogBreed(breedName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getDogBreed(breedName)
        }

    }

    fun saveImageToGallery(bitmap: Bitmap, context: Context, contentResolver: ContentResolver) {
        Helper.saveImageToGallery(bitmap = bitmap, context, contentResolver)
    }

    fun shareImageToOther(bitmap: Bitmap, context: Context, contentResolver: ContentResolver) {
        Helper.shareImage(bitmap = bitmap, context, contentResolver)
    }
}