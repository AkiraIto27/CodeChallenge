package com.example.akiraito.codechallenge.weget.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.akiraito.codechallenge.databinding.LayoutFooterLoadingBinding

class LoadingFooterAdapter : RecyclerView.Adapter<LoadingFooterVH>() {
    private var isLoading: Boolean = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadingFooterVH =
        LoadingFooterVH(
            LayoutFooterLoadingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = if (isLoading) 1 else 0

    override fun onBindViewHolder(holder: LoadingFooterVH, position: Int) {
        holder.bind(isLoading)
    }

    fun isLoading() = isLoading

    fun setLoading(isLoad: Boolean) {
        if (this.isLoading == isLoad) {
            return
        }
        this.isLoading = isLoad
        notifyDataSetChanged()
    }
}

class LoadingFooterVH(private val binding: LayoutFooterLoadingBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(isVisible: Boolean) {
        binding.isLoading = isVisible
    }
}
