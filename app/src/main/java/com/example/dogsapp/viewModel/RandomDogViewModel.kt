package com.example.dogsapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogsapp.model.RandomDog
import com.example.dogsapp.repository.DogRepository
import com.example.dogsapp.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RandomDogViewModel @Inject constructor(val repository: DogRepository) : ViewModel() {

    val liveDataRandom: LiveData<Response<RandomDog>>
        get() = repository.liveDataRandom

    fun getRandomDog() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRandomDog()
        }
    }
}