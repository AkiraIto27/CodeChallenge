package com.example.akiraito.codechallenge.weget.recycler.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.models.SlideModel
import com.example.akiraito.codechallenge.databinding.ItemSlideImageLayoutBinding
import com.example.akiraito.codechallenge.extention.inflater
import com.example.akiraito.codechallenge.weget.recycler.viewholder.SlideImageVH

class SlideImageAdapter(
    private val onItemClickListener: (String) -> Unit
) : RecyclerView.Adapter<SlideImageVH>() {
    private val slideImageList = arrayListOf<SlideModel>()

    fun updateSlideImage(imageList: ArrayList<SlideModel>) {
        slideImageList.clear()
        slideImageList.addAll(imageList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideImageVH {
        return SlideImageVH(
            ItemSlideImageLayoutBinding.inflate(parent.context.inflater(), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SlideImageVH, position: Int) {
        holder.bind(slideImageList, onItemClickListener)
    }

    override fun getItemCount() = 1
}