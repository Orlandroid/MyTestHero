package com.example.presentation.ui.lista

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entities.local.Earthquake
import com.example.presentation.databinding.ItemEarthquakeBinding
import com.example.presentation.extensions.click


class ListAdapter(private val clickOnEarthquake: (Earthquake) -> Unit) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var listOfEarthquake: List<Earthquake> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<Earthquake>) {
        listOfEarthquake = list
        notifyDataSetChanged()
    }


    class ViewHolder(private val binding: ItemEarthquakeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            earthquake: Earthquake,
            clickOnEarthquake: (Earthquake) -> Unit
        ) = with(binding) {
            tvValueLugar.text = earthquake.place
            tvValueMagnitud.text = earthquake.magnitude.toString()
            tvValueLatitude.text = earthquake.latitude.toString()
            tvValueLongitude.text = earthquake.longitude.toString()
            root.click {
                clickOnEarthquake(earthquake)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemEarthquakeBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listOfEarthquake[position]
        holder.bind(item, clickOnEarthquake)
    }

    override fun getItemCount() = listOfEarthquake.size



}
