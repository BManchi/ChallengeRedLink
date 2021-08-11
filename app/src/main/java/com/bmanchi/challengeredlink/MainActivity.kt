package com.bmanchi.challengeredlink

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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

    lateinit var viewModel: MainViewModel
    private val rvAdapter by lazy { AlbumAdapter { album -> albumClicked(album) } }

    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Init VModel with custom ProviderFactory
        val viewModelProviderFactory = MainViewModelProviderFactory(application)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(MainViewModel::class.java)

        setupRecyclerView()
        initObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val item = menu?.findItem(R.id.app_bar_search)
        searchView = item?.actionView as SearchView

        // Restore pending query after activity destroy
        // Bug fix rom https://www.youtube.com/watch?v=z3q943EPP5s
        val pendingQuery = viewModel.currentSearch.value
        if (pendingQuery != null && pendingQuery.isNotEmpty()) {
            item.expandActionView()
            searchView.setQuery(pendingQuery, false)
        }

        // Filter the RecyclerView's items
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                rvAdapter.filter.filter(newText)
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
        viewModel.liveDataAlbums.observe(this, Observer {
            it?.let {
                rvAdapter.setData(it)
            }
        })

        viewModel.selectedPhotos.observe(this, Observer {
            it?.let {
                addFragment(PhotosFragment())
            }
        })

        viewModel.errorDescription.observe(this, Observer {
            it?.let {
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

    // OnClick Album listener
    private fun albumClicked(album: AlbumsItem) {
        viewModel.getPhotos(album.id)
    }

    /**
     * Resets selections so Observer doesn't call the fragment again.
     * Bypasses the second back-press bug (focus of SearchView)
     */
    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is PhotosFragment) {
                viewModel.selectedPhotos.value = null
            }
        }
        super.onBackPressed()
    }
}