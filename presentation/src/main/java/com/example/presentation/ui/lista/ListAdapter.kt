package com.example.presentation.ui.lista

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entities.remote.Feature
import com.example.domain.entities.remote.Properties
import com.example.presentation.databinding.ItemEarthquakeBinding
import com.example.presentation.extensions.click


class ListAdapter(private val clickOnEarthquake: (Properties) -> Unit) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var listOfEarthquake: List<Feature> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<Feature>) {
        listOfEarthquake = list
        notifyDataSetChanged()
    }


    class ViewHolder(private val binding: ItemEarthquakeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(properties: Properties, clickOnEarthquake: (Properties) -> Unit) = with(binding) {
            tvValueLugar.text = properties.place
            tvValueMagnitud.text = properties.mag.toString()
            root.click {
                clickOnEarthquake(properties)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemEarthquakeBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfEarthquake[position].properties, clickOnEarthquake)
    }

    override fun getItemCount() = listOfEarthquake.size


}
