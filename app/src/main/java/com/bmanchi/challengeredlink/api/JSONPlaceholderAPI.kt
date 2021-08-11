package com.bmanchi.challengeredlink.api

import com.bmanchi.challengeredlink.models.AlbumsItem
import com.bmanchi.challengeredlink.models.PhotosItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface JSONPlaceholderAPI {

    @GET("albums")
    fun getAlbums() :Call<ArrayList<AlbumsItem>>

    @GET("photos")
    fun getPhotosFromAlbum(
        @Query("albumId")
        albumId: Int
    ): Call<ArrayList<PhotosItem>>

}