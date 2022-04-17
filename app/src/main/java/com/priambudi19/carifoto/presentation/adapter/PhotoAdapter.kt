package com.priambudi19.carifoto.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.priambudi19.carifoto.data.model.Photo
import com.priambudi19.carifoto.databinding.FotoListItemBinding

class PhotoAdapter(
    var onClick: ((Photo) -> Unit)? = null
) : RecyclerView.Adapter<PhotoListViewHolder>() {
    private val listPhoto: MutableList<Photo> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListViewHolder {
        return PhotoListViewHolder(
            FotoListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<Photo>?) {
        listPhoto.clear()
        if (list.isNullOrEmpty().not()) {
            listPhoto.addAll(list!!)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: PhotoListViewHolder, position: Int) {
        val photo = listPhoto[position]
        holder.bind(photo)
        holder.itemView.setOnClickListener { onClick?.invoke(photo) }
    }

    override fun getItemCount(): Int = listPhoto.size
}