package com.bmanchi.challengeredlink.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bmanchi.challengeredlink.repos.AlbumsRepository

class MainViewModelProviderFactory (
    val app: Application
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(app) as T
        }
    }