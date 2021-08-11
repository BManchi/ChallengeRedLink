package com.bmanchi.challengeredlink.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Custom VM Provider to share a single VM between activities/fragments
 * Grants data persistence between activities and its' lifecycles
 * e.g. without this, screen rotation detached the VM from Fragment's recyclerview
 */

class MainViewModelProviderFactory (
    val app: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(app) as T
    }
}