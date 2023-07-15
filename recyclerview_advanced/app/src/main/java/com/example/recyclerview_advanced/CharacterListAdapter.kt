package com.example.recyclerview_advanced

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerview_advanced.databinding.CharacterItemBinding

class CharacterListAdapter : PagingDataAdapter<Character, CharactersViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        return CharactersViewHolder(
            CharacterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            name.text = item?.name ?: ""
//            indicator.text = item?.name ?: ""
            status.text = item?.status ?: ""
            species.text = item?.species ?: ""
            location.text = item?.location?.name ?: ""
            item?.let {
                Glide
                    .with(photo.context)
                    .load(it.image)
                    .into(photo)
            }
        }
    }
}

class CharactersViewHolder(val binding: CharacterItemBinding) : RecyclerView.ViewHolder(binding.root)

class DiffUtilCallback : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
        oldItem == newItem
}