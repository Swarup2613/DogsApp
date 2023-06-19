package com.example.dogsapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogsapp.model.DogBreed
import com.example.dogsapp.model.DogBreedList
import com.example.dogsapp.repository.DogRepository
import com.example.dogsapp.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogsAllBreedViewModel @Inject constructor(val repository: DogRepository) : ViewModel() {

    val liveDataDogBreedList: LiveData<Response<DogBreedList>>
        get() = repository.liveDataDogBreedList



        fun getDogBreedList() {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getDogBreedList()
            }




    }
}