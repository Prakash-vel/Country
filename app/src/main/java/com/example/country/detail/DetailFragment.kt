package com.example.country.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.country.databasehander.CountryDatabase
import com.example.country.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailBinding.inflate(inflater)


        val application = requireNotNull(this.activity).application
        val dataSource = CountryDatabase.getInstance(application).countryDatabaseDao

        val selectedId = DetailFragmentArgs.fromBundle(requireArguments()).selectedProperty

        val detailViewModelFactory = DetailViewModelFactory(dataSource, selectedId,application)

        binding.detailViewModel =
            ViewModelProviders.of(this, detailViewModelFactory).get(DetailViewModel::class.java)



        binding.lifecycleOwner=this
        binding.root


        return binding.root
    }

}