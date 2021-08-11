package com.bmanchi.challengeredlink.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bmanchi.challengeredlink.models.AlbumsItem
import com.bmanchi.challengeredlink.models.PhotosItem
import com.bmanchi.challengeredlink.repos.JSONPlaceholderRepository

class MainViewModel(app: Application) : ViewModel() {

    private val repo = JSONPlaceholderRepository()
    val liveDataAlbums: LiveData<ArrayList<AlbumsItem>> = Transformations.map(repo.albumResponse) { it }
    var selectedPhotos = MutableLiveData<ArrayList<PhotosItem>?>()
    val currentSearch = MutableLiveData<String>()
    val errorDescription: LiveData<String> = Transformations.map(repo.errorResponse) { it }

    init {
        if (liveDataAlbums.value.isNullOrEmpty()) {
            getAlbums()
        }
        repo.photoResponse.observeForever {
            selectedPhotos.value = it
        }
    }

    fun getAlbums() {
        repo.getAlbums()
    }

    fun getPhotos(albumId: Int) {
        repo.getPhotos(albumId)
    }
}