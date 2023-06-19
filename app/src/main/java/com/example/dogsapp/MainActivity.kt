package com.example.dogsapp


import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.drawToBitmap
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dogsapp.adapter.DogBreedAdapter
import com.example.dogsapp.databinding.ActivityMainBinding
import com.example.dogsapp.utils.Constants
import com.example.dogsapp.utils.Helper
import com.example.dogsapp.utils.RecyclerViewTouchListener
import com.example.dogsapp.utils.Response
import com.example.dogsapp.utils.SharedPreferenceUtils
import com.example.dogsapp.viewModel.DogBreedViewModel
import com.example.dogsapp.viewModel.DogsAllBreedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var dogbreedViewModel: DogBreedViewModel
    lateinit var dogsAllBreedViewModel: DogsAllBreedViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var sharedPreferenceUtils: SharedPreferenceUtils
    val arrayList = ArrayList<String>()
    val dogArrayList = ArrayList<String>()
    lateinit var dogBreedAdapter: DogBreedAdapter
    var dogBreedname = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initialize()
        fetchDogBreedlist()
        getDogBreedList()
        getDogBreed()
    }


    private fun initialize() {
        dogsAllBreedViewModel = ViewModelProvider(this).get(DogsAllBreedViewModel::class.java)
        dogbreedViewModel = ViewModelProvider(this).get(DogBreedViewModel::class.java)
        sharedPreferenceUtils = SharedPreferenceUtils(this)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 2)
        binding.rvDogBreed.layoutManager = layoutManager
        binding.rvDogBreed.itemAnimator = DefaultItemAnimator()
        binding.rvDogBreed.setHasFixedSize(true)
        dogBreedAdapter = DogBreedAdapter(dogArrayList, this, layoutInflater)
        binding.rvDogBreed.adapter = dogBreedAdapter


        binding.actSearchDogBreedName.setOnItemClickListener(OnItemClickListener { parent, arg1, pos, id ->
            lifecycleScope.launch(Dispatchers.IO) {
                dogBreedname = parent.getItemAtPosition(pos).toString()
                dogbreedViewModel.getDogBreed(dogBreedname)
            }

        })



        binding.rvDogBreed.addOnItemTouchListener(
            RecyclerViewTouchListener(applicationContext,
                binding.rvDogBreed,
                object : RecyclerViewTouchListener.ClickListener {

                    override fun onClick(view: View?, position: Int) {
                        showCustomDialog(dogArrayList.get(position))
                    }

                    override fun onLongClick(view: View?, position: Int) {

                    }
                })
        )

    }

    private fun fetchDogBreedlist() {

        GlobalScope.launch(Dispatchers.IO) {
            val job = lifecycleScope.launch(Dispatchers.IO) {
                dogsAllBreedViewModel.getDogBreedList()
            }
            //job.join()
        }


    }

    private fun getDogBreed() {

        dogbreedViewModel.liveDataDogBreed.observe(this) {
            when (it) {
                is Response.Loading -> {

                    binding.progressBar.visibility = View.VISIBLE
                }

                is Response.Success -> {
                    it.data?.let {
                        Log.d("TAG", "getDogBreed:${it.message} ")
                        setDogImages(it.message)
                    }
                }

                is Response.Error -> {

                    it.data?.let {
                        binding.progressBar.visibility = View.GONE
                        Helper.showToast(this, it.message.toString())

                    }

                }
            }
        }
    }

    private fun setDogImages(message: List<String>) {
        dogArrayList.clear()
        binding.progressBar.visibility = View.GONE
        binding.rvDogBreed.visibility = View.VISIBLE
        dogArrayList.addAll(message)
        dogBreedAdapter.notifyDataSetChanged()
        binding.tvBreed.visibility = View.VISIBLE
        binding.tvBreed.text = "Breed Name:-${dogBreedname.uppercase()}"
        binding.actSearchDogBreedName.text.clear()

        /* val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
         imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)*/
    }

    private fun getDogBreedList() {
        dogsAllBreedViewModel.liveDataDogBreedList.observe(this) {
            when (it) {
                is Response.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Response.Success -> {
                    it.data?.let {
                        binding.progressBar.visibility = View.GONE
                        arrayList.addAll(it.message)
                        val adapter = ArrayAdapter<String>(
                            this,
                            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                            arrayList
                        )
                        binding.actSearchDogBreedName.setAdapter(adapter)

                        setFavoriteBreedList()

                    }
                }

                is Response.Error -> {
                    it.data?.let {
                        binding.progressBar.visibility = View.GONE
                        Helper.showToast(this, it.message.toString())

                    }

                }
            }
        }

    }

    private fun setFavoriteBreedList() {

        GlobalScope.launch(Dispatchers.IO) {
            dogBreedname =
                sharedPreferenceUtils.getSharedPreferenceDataString(Constants.FAVOURITE_BREED)
                    .toString()

            if (!dogBreedname.equals("null")) {
                val job = lifecycleScope.launch(Dispatchers.IO) {
                    dogbreedViewModel.getDogBreed(dogBreedname.toString())
                }

                // job.join()
            }

            binding.tvBreed.text = dogBreedname

        }

    }

    fun showCustomDialog(dogImageUrl: String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.select_action_dialog)
        val iv_selected_dog = dialog.findViewById<ImageView>(R.id.iv_selected_dog)
        val tv_breedName = dialog.findViewById<TextView>(R.id.tv_breedName)
        val btn_save = dialog.findViewById<Button>(R.id.btn_save)
        val btn_share = dialog.findViewById<Button>(R.id.btn_share)
        val btn_favourite_dog = dialog.findViewById<Button>(R.id.btn_favourite_dog)
        val btn_favourite_breed = dialog.findViewById<Button>(R.id.btn_favourite_breed)

        Log.d("TAG", "showCustomDialog: ${dogImageUrl}")
        tv_breedName.text = "Breed Name: ${dogBreedname.uppercase()}"
        Helper.setImageWithGlide(this, dogImageUrl, iv_selected_dog)
        btn_save.setOnClickListener {
            val bitmap = iv_selected_dog.drawToBitmap()
            dogbreedViewModel.saveImageToGallery(bitmap, this, contentResolver)
        }

        btn_share.setOnClickListener {
            val bitmap = iv_selected_dog.drawToBitmap()
            dogbreedViewModel.shareImageToOther(bitmap, this, contentResolver)
        }

        btn_favourite_dog.setOnClickListener {
            val response = sharedPreferenceUtils.setSharePrefrenceDataString(
                Constants.FAVOURITE_DOG,
                dogImageUrl
            )
            if (response)
                Helper.showToast(this, "Favourite Dog has been selected")

        }
        btn_favourite_breed.setOnClickListener {
            val response = sharedPreferenceUtils.setSharePrefrenceDataString(
                Constants.FAVOURITE_BREED, dogBreedname
            )
            if (response)
                Helper.showToast(this, "Favourite Breed has been selected")
        }
        dialog.show()
    }


}