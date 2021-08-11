package com.bmanchi.challengeredlink.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bmanchi.challengeredlink.MainActivity
import com.bmanchi.challengeredlink.R
import com.bmanchi.challengeredlink.adapters.PhotoAdapter
import com.bmanchi.challengeredlink.repos.AlbumsRepository
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.photos_fragment.*

class PhotosFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private val rvAdapter by lazy { PhotoAdapter()}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.photos_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()
        rvAdapter.setData(viewModel.selectedPhotos.value!!)
    }

    private fun setupRecyclerView() {
        rvPhotos.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onDetach() {
        super.onDetach()
        (activity as AppCompatActivity).supportActionBar?.show()
        viewModel.selectedPhotos.value = null
    }



}