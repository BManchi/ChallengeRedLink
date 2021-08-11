package com.bmanchi.challengeredlink.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bmanchi.challengeredlink.R
import com.bmanchi.challengeredlink.models.AlbumsItem
import com.bmanchi.challengeredlink.models.PhotosItem
import com.bmanchi.challengeredlink.ui.main.PhotosFragment
import kotlinx.android.synthetic.main.item_album.view.*
import kotlinx.android.synthetic.main.item_photo.view.*


class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.PhotoViewholder>() {

    private var data = arrayListOf<PhotosItem>()

    /*private val differCallback = object : DiffUtil.ItemCallback<PhotosItem>() {
        override fun areItemsTheSame(oldItem: PhotosItem, newItem: PhotosItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PhotosItem, newItem: PhotosItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewholder {
        return PhotoViewholder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_photo,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotoViewholder, position: Int) {
        val photo = data[position]
        holder.apply {
            //Glide.with(context).load(photo.thumbnailUrl).into(image)
            image.load(photo.thumbnailUrl){
                placeholder(R.drawable.ic_image)
                error(R.drawable.ic_error)
                fallback(R.drawable.ic_error)
            }
            id.text = photo.id.toString()
            title.text = photo.title
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(newList: ArrayList<PhotosItem>){
        data = newList
        notifyDataSetChanged()
    }

    inner class PhotoViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id: TextView = itemView.textViewPhotoId
        val title: TextView = itemView.textViewPhotoTitle
        val image: ImageView = itemView.imageViewPhoto
    }
}