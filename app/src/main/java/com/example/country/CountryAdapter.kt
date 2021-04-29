package com.example.country

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.country.databasehander.CountryData
import com.example.country.databinding.GridItemCountryBinding

class CountryAdapter(val onClickListener: OnClickListener): ListAdapter<CountryData,CountryAdapter.ViewHolder>(DiffUtillCallBack()) {
    class OnClickListener(val clickListener: (countryData: CountryData) -> Unit) {
        fun onClick(countryData: CountryData) = clickListener(countryData)
    }
    class ViewHolder(val binding: GridItemCountryBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CountryData) {

            binding.countryData=data
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(GridItemCountryBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val data=getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(data)
        }
        holder.bind(data)
    }
}

class DiffUtillCallBack: DiffUtil.ItemCallback<CountryData>() {
    override fun areItemsTheSame(oldItem: CountryData, newItem: CountryData): Boolean {
        return oldItem.CountryId == newItem.CountryId
    }

    override fun areContentsTheSame(oldItem: CountryData, newItem: CountryData): Boolean {
        return oldItem == newItem
    }

}
