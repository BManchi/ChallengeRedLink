package com.bmanchi.challengeredlink.repos

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import com.bmanchi.challengeredlink.R
import com.bmanchi.challengeredlink.api.RetrofitInstance
import com.bmanchi.challengeredlink.models.AlbumsItem
import com.bmanchi.challengeredlink.models.PhotosItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//TODO cambiar nombre general JsonAPIPlaceholder
class AlbumsRepository {

    var albumResponse = MutableLiveData<ArrayList<AlbumsItem>>()
    var errorResponse = MutableLiveData<String>()
    var photoResponse = MutableLiveData<ArrayList<PhotosItem>>()

    fun getAlbums() = RetrofitInstance.api.getAlbums().enqueue(
        object : Callback<ArrayList<AlbumsItem>> {
            override fun onResponse(
                call: Call<ArrayList<AlbumsItem>>,
                response: Response<ArrayList<AlbumsItem>>
            ) {
                if (response.isSuccessful){
                    albumResponse.postValue(response.body())
                } else {
                    errorResponse.postValue(response.message().toString())
                }
            }

            override fun onFailure(call: Call<ArrayList<AlbumsItem>>, t: Throwable) {
                errorResponse.postValue("Error Genérico")
            }
        }
    )

    fun getPhotos(albumId : Int) = RetrofitInstance.api.getPhotosFromAlbum(albumId).enqueue(
        object : Callback<ArrayList<PhotosItem>> {
            override fun onResponse(
                call: Call<ArrayList<PhotosItem>>,
                response: Response<ArrayList<PhotosItem>>
            ) {
                if (response.isSuccessful){
                    photoResponse.postValue(response.body())
                } else {
                    errorResponse.postValue(response.message().toString())
                }
            }

            override fun onFailure(call: Call<ArrayList<PhotosItem>>, t: Throwable) {
                errorResponse.postValue("Error Genérico")
            }
        }
    )
}