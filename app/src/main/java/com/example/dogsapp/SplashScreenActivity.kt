package com.example.dogsapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.dogsapp.utils.Constants
import com.example.dogsapp.utils.Helper
import com.example.dogsapp.utils.Response
import com.example.dogsapp.utils.SharedPreferenceUtils
import com.example.dogsapp.viewModel.RandomDogViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    val iv_favourite_dog
        get() = findViewById<ImageView>(R.id.iv_favourite_dog)
    val progress_bar
        get() = findViewById<ProgressBar>(R.id.progress_bar)
    lateinit var sharedPreferenceUtils: SharedPreferenceUtils
    lateinit var randomDogViewModel: RandomDogViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        initialize()

        val SPLASH_SCREEN = 5000
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN.toLong())
    }

    private fun initialize() {

        sharedPreferenceUtils = SharedPreferenceUtils(this)
        randomDogViewModel = ViewModelProvider(this).get(RandomDogViewModel::class.java)
        val favouriteDog =
            sharedPreferenceUtils.getSharedPreferenceDataString(Constants.FAVOURITE_DOG)

        if (favouriteDog.equals("null")) {
            lifecycleScope.launch(Dispatchers.IO) {
                randomDogViewModel.getRandomDog()

            }
        } else {
            iv_favourite_dog.visibility = View.VISIBLE
            Helper.setImageWithGlide(this, favouriteDog.toString(), iv_favourite_dog)
        }
        getRandomDog()
    }

    private fun getRandomDog() {

        randomDogViewModel.liveDataRandom.observe(this) {

            when (it) {
                is Response.Loading -> {

                    progress_bar.visibility = View.VISIBLE

                }

                is Response.Success -> {
                    if (it.data?.status == "success") {
                        progress_bar.visibility = View.GONE
                        iv_favourite_dog.visibility = View.VISIBLE
                        Helper.setImageWithGlide(this, it.data.message, iv_favourite_dog)
                    } else {
                        Helper.showToast(this, it.data.toString())

                    }

                }

                is Response.Error -> {
                    progress_bar.visibility = View.GONE
                    Helper.showToast(this, it.errorMessage.toString())

                }
            }
        }
    }
}