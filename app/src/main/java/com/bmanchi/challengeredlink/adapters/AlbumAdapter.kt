package com.bmanchi.challengeredlink.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bmanchi.challengeredlink.R
import com.bmanchi.challengeredlink.models.AlbumsItem
import kotlinx.android.synthetic.main.item_album.view.*


class AlbumAdapter(val onClick: (AlbumsItem) -> Unit) : RecyclerView.Adapter<AlbumAdapter.AlbumViewholder>(), Filterable {

    private var data = arrayListOf<AlbumsItem>()
    private var dataFull = arrayListOf<AlbumsItem>()

    inner class AlbumViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id : TextView = itemView.textViewAlbumId
        val title : TextView = itemView.textViewAlbumTitle

        fun setItem(item: AlbumsItem) {
            itemView.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewholder {
        return AlbumViewholder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_album,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AlbumViewholder, position: Int) {
        val currentAlbum = data[position]
        holder.apply {
            id.text = currentAlbum.id.toString()
            title.text = currentAlbum.title
            setItem(currentAlbum)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(newList: ArrayList<AlbumsItem>){
        data = newList
        dataFull = ArrayList(newList)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = ArrayList<AlbumsItem>()
                if (constraint.isNullOrEmpty()) {
                    filteredList.addAll(dataFull)
                } else {
                    val stringFilter = constraint.toString().toLowerCase().trim()
                    dataFull.forEach{
                        if (it.title.toLowerCase().contains(stringFilter)) {
                            filteredList.add(it)
                        }
                    }
                }
                return FilterResults().apply { values = filteredList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                data.clear()
                data.addAll(results!!.values as Collection<AlbumsItem>)
                notifyDataSetChanged()
            }
        }
    }
}