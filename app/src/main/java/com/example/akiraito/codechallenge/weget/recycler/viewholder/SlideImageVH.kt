package com.example.akiraito.codechallenge.weget.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.akiraito.codechallenge.databinding.ItemSlideImageLayoutBinding

class SlideImageVH(
    val binding: ItemSlideImageLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        movieList: ArrayList<SlideModel>,
        onItemClickListener: (String) -> Unit
    ) {
        binding.imageSlider.also {
            it.setImageList(movieList)
            it.setItemClickListener(object : ItemClickListener {
                override fun onItemSelected(position: Int) {
                    // 映画詳細画面に遷移
                    movieList[position].title?.let {
                        onItemClickListener.invoke(it)
                    }
                }
            })
        }
    }
}