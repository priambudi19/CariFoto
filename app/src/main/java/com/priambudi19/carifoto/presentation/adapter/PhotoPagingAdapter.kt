package com.priambudi19.carifoto.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.priambudi19.carifoto.data.model.Photo
import com.priambudi19.carifoto.databinding.FotoListItemBinding

class PhotoPagingAdapter(
    var onClick: ((Photo) -> Unit)? = null
) : PagingDataAdapter<Photo, PhotoListViewHolder>(
    object : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListViewHolder {
        return PhotoListViewHolder(
            FotoListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotoListViewHolder, position: Int) {
        val photo = getItem(position) as Photo
        holder.bind(photo)
        holder.itemView.setOnClickListener { onClick?.invoke(photo) }
    }

}