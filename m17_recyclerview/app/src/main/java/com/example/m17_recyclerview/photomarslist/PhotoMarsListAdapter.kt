package com.example.m17_recyclerview.photomarslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.m17_recyclerview.databinding.PhotoMarsItemBinding
import com.example.m17_recyclerview.models.PhotoMars


class PhotoMarsListAdapter : PagingDataAdapter<PhotoMars, PhotoMarsViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoMarsViewHolder {
        return PhotoMarsViewHolder(
            PhotoMarsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotoMarsViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            roverValue.text = item?.rover?.name ?: ""
            dateValue.text = item?.earth_date ?: ""
            cameraValue.text = item?.camera?.name ?: ""
            solValue.text = item?.sol.toString()
            item?.let {
                Glide
                    .with(photo.context)
                    .load(it.img_src)
                    .into(photo)
            }
        }
    }
}

class PhotoMarsViewHolder(val binding: PhotoMarsItemBinding) : RecyclerView.ViewHolder(binding.root)

class DiffUtilCallback : DiffUtil.ItemCallback<PhotoMars>() {
    override fun areItemsTheSame(oldItem: PhotoMars, newItem: PhotoMars): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PhotoMars, newItem: PhotoMars): Boolean =
        oldItem == newItem
}