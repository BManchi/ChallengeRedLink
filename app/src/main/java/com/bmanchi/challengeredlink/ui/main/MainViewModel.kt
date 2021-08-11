package com.bmanchi.challengeredlink.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bmanchi.challengeredlink.api.RetrofitInstance
import com.bmanchi.challengeredlink.models.*
import com.bmanchi.challengeredlink.repos.AlbumsRepository
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(val app: Application) : ViewModel() {

    private val repo = AlbumsRepository()
    val liveDataAlbums : LiveData<ArrayList<AlbumsItem>> = Transformations.map(repo.albumResponse) { it }
    //var liveDataPhotos : MutableLiveData<ArrayList<PhotosItem>> = Transformations.map(repo.photoResponse) { MutableLiveData<Arraylist<PhotosItem>>(it) }
    var selectedPhotos = MutableLiveData<ArrayList<PhotosItem>?>()
    val searching = MutableLiveData(false)
    val currentSearch = MutableLiveData<String>()
    val errorDescription : LiveData<String> = Transformations.map(repo.errorResponse) { it }

    init {
        if (liveDataAlbums.value.isNullOrEmpty()) {
            getAlbums()
        }
        repo.photoResponse.observeForever{
            selectedPhotos.value = it
        }
    }

    fun getAlbums(){
        repo.getAlbums()
    }

    fun getPhotos(albumId : Int) {
        repo.getPhotos(albumId)
    }
}