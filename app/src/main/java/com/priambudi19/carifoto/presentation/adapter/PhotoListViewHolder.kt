package com.priambudi19.carifoto.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.priambudi19.carifoto.R
import com.priambudi19.carifoto.data.model.Photo
import com.priambudi19.carifoto.databinding.FotoListItemBinding

class PhotoListViewHolder(private val binding: FotoListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo){
            binding.apply {
                txtName.text = photo.user.name
                imgUser.load(photo.user.profileImage.medium){
                    placeholder(R.drawable.ic_foto_loading)
                    error(R.drawable.ic_broken)
                    transformations(CircleCropTransformation())
                }
                imgListItem.load(photo.urls.regular){
                    placeholder(R.drawable.ic_foto_loading)
                    error(R.drawable.ic_broken)
                }
            }
        }
    }
