package jp.gr.java_conf.jnikd.donkimap.view

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import jp.gr.java_conf.jnikd.donkimap.R
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val map = googleMap

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true;
            map.uiSettings.isMyLocationButtonEnabled = true;
        }

        val position = LatLng(35.6489301, 139.6926504)
        map.addMarker(MarkerOptions().position(position).title("中目黒店"))
        map.moveCamera(CameraUpdateFactory.newLatLng(position))

        map.setOnMyLocationButtonClickListener {
            // 住所から緯度経度
            val gcoder = Geocoder(this, Locale.getDefault());
            val address = gcoder.getFromLocationName("福岡県久留米市東合川2-2-1", 1).get(0)
            val latitude = address.latitude
            val longitude = address.longitude

            // 現在位置
//            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//            val location: Location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            map.moveCamera(CameraUpdateFactory.newLatLng(LatLng(latitude, longitude)))
            false
        }
    }
}
