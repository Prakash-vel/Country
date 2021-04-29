package com.example.country.title

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.SearchView.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.country.CountryAdapter
import com.example.country.databasehander.CountryDatabase
import com.example.country.databinding.FragmentTitleBinding
import com.example.country.databinding.GridItemCountryBinding
import kotlinx.android.synthetic.main.fragment_title.*


class TitleFragment : Fragment() {

    private lateinit var binding: FragmentTitleBinding

    private lateinit var titleViewModel:TitleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val application = requireNotNull(this.activity).application

        val dataSource = CountryDatabase.getInstance(application).countryDatabaseDao

        val viewModelFactory=TitleViewModelFactory(dataSource)


        titleViewModel= ViewModelProviders.of(this,viewModelFactory).get(TitleViewModel::class.java)

        titleViewModel.apiResult.observe(viewLifecycleOwner, Observer {

        })
        binding=FragmentTitleBinding.inflate(inflater, container, false)

        binding.lifecycleOwner=this

        //val layoutManager=GridLayoutManager(this.context,2)
        val layoutManager=StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)

       // val layoutManager=LinearLayoutManager(this.context)

        binding.recyclerView.layoutManager=layoutManager

        binding.titleViewModel=titleViewModel

        val adapter=CountryAdapter(CountryAdapter.OnClickListener {
            titleViewModel.showDetailView(it)
        })
        titleViewModel.selectedCountry.observe(this.viewLifecycleOwner, Observer {
            if(it != null){
                this.findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToDetailFragment(it.CountryId))
                titleViewModel.showDetailViewComplete()
            }

        })
        binding.recyclerView.adapter=adapter
        titleViewModel.dbResult.observe(this.viewLifecycleOwner, Observer {
            Log.i("hello","value changed")
            adapter.submitList(it)
        })
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                //Performs search when user hit the search button on the keyboard
//                if (bestCities.contains(p0)) {
//                    adapter.filter.filter(p0)
//                } else {
//                    Toast.makeText(this@MainActivity, "No match found", Toast.LENGTH_SHORT).show()


                binding.searchView.clearFocus()
                Log.i("hello","$p0")
                titleViewModel.getProperties(p0)

                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
//                //Start filtering the list as user start entering the characters
//                adapter.filter.filter(p0)
                titleViewModel.getProperties(p0)
                return false
            }

        })
        fun close(int: Int){
            binding.searchView.clearFocus()
        }

        binding.searchView.setOnCloseListener(object: OnCloseListener {
            override fun onClose():Boolean {
                Log.d("hello", "search closed")
                titleViewModel.getProperties(null)
                binding.searchView.clearFocus()
                return true
            }
        })


        return binding.root

    }

}