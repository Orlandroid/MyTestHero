package com.example.presentation.ui.detail

import androidx.navigation.fragment.navArgs
import com.example.domain.entities.local.Earthquake
import com.example.domain.extensions.fromJson
import com.example.presentation.R
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentDetailBinding
import com.example.presentation.extensions.showLog
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail),
    OnMapReadyCallback {

    private val args: DetailFragmentArgs by navArgs()

    override fun setUpUi() {
        binding.earthquake = args.earthquake.fromJson<Earthquake>()
        initMap()
    }

    private fun initMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(mMap: GoogleMap) {
        showLog("Maps Ready")
        val earthquake = args.earthquake.fromJson<Earthquake>()
        val latLng = LatLng(earthquake.latitude, earthquake.longitude)
        mMap.addMarker(MarkerOptions().position(latLng).title(earthquake.place))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
    }


}