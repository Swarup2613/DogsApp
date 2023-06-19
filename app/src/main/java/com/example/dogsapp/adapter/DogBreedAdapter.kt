package com.example.dogsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dogsapp.R
import com.example.dogsapp.utils.CustomDialog
import com.example.dogsapp.utils.Helper
import com.example.dogsapp.viewModel.DogBreedViewModel

class DogBreedAdapter(
    val dogList: ArrayList<String>, val context: Context, val layoutInflater: LayoutInflater
) : RecyclerView.Adapter<DogBreedAdapter.DogBreedViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): DogBreedViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.dog_image_layout, parent, false)
        return DogBreedViewHolder(view)

    }

    override fun onBindViewHolder(holder: DogBreedViewHolder, position: Int) {
        val dogImage = dogList.get(position)
        Helper.setImageWithGlide(context, dogImage, holder.iv_dog_image)
    }

    override fun getItemCount(): Int {
        return dogList.size
    }


    inner class DogBreedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val iv_dog_image
            get() = itemView.findViewById<ImageView>(R.id.iv_dog_image)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

        }
    }
}