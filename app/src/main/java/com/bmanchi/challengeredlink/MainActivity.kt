package com.bmanchi.challengeredlink

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bmanchi.challengeredlink.adapters.AlbumAdapter
import com.bmanchi.challengeredlink.models.AlbumsItem
import com.bmanchi.challengeredlink.ui.main.MainViewModel
import com.bmanchi.challengeredlink.ui.main.MainViewModelProviderFactory
import com.bmanchi.challengeredlink.ui.main.PhotosFragment
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel : MainViewModel
    //
    private val rvAdapter by lazy { AlbumAdapter{ album -> albumSeleccionado(album)} }

    private lateinit var searchView: SearchView

    private fun albumSeleccionado(album : AlbumsItem) {
        Log.d("SELECCION", "album pasado ${album.id}")
        viewModel.getPhotos(album.id)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val viewModelProviderFactory = MainViewModelProviderFactory(application)

        // Init VModel with custom ProviderFactory
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(MainViewModel::class.java)

        setupRecyclerView()
        initObservers()
    }

    override fun onResume() {
        super.onResume()
        Log.d("oncreate", "onCreate: volvemos del query? ${viewModel.currentSearch.value}")
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        val item = menu?.findItem(R.id.app_bar_search)
        searchView = item?.actionView as SearchView

        val pendingQuery = viewModel.currentSearch.value
        if (pendingQuery != null && pendingQuery.isNotEmpty()) {
            item.expandActionView()
            searchView.setQuery(pendingQuery, false)
        }
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                rvAdapter.filter.filter(newText)
                //viewModel.currentSearch.value = newText
                Log.d("query", "onQueryTextChange: $newText")
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                rvAdapter.filter.filter(query)
                return false
            }
        })
        return true
    }

    private fun initObservers() {
        viewModel.liveDataAlbums.observe(this, Observer{
            it?.let {
                rvAdapter.setData(it)
            }
        })

        viewModel.selectedPhotos.observe(this, Observer {
            it?.let {
                Log.d("click", "initObservers: photoId ${it[1].id}")
                addFragment(PhotosFragment())
            }
        })

        viewModel.errorDescription.observe(this, Observer {
            it?.let {
                Log.d("TAG", "initObservers: error $it")
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupRecyclerView() {
        rvAlbums.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    fun addFragment(fragment: Fragment) {
        if (supportFragmentManager.findFragmentByTag(fragment.javaClass.simpleName) == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_frame, fragment, fragment.javaClass.simpleName)
                .addToBackStack(fragment.javaClass.simpleName)
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        searchView.setOnQueryTextListener(null)
    }

}