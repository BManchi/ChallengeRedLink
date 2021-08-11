package com.bmanchi.challengeredlink.models

import java.io.Serializable

class Albums : ArrayList<AlbumsItem>()

data class AlbumsItem(
    var id: Int,
    val title: String,
    val userId: Int
) : Serializable