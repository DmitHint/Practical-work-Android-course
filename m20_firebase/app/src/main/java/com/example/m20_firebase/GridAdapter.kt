package com.example.m20_firebase


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.m20_firebase.databinding.PhotoItemBinding

class GridAdapter(private val context: Context, private val allPhotos: List<Photo>) :
    RecyclerView.Adapter<GridViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        return GridViewHolder(
            PhotoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = allPhotos.size

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val item = allPhotos[position]
        with(holder.binding) {
            Glide.with(photo.context)
                .load(item.uri)
                .into(photo)
        }
    }
}


class GridViewHolder(val binding: PhotoItemBinding) : RecyclerView.ViewHolder(binding.root)
