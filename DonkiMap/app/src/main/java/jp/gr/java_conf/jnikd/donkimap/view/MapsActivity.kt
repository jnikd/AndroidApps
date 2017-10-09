package jp.gr.java_conf.jnikd.donkimap.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
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
import jp.gr.java_conf.jnikd.donkimap.entity.StoreList
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // 現在位置を利用可能にする
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            googleMap.isMyLocationEnabled = true;
            googleMap.uiSettings.isMyLocationButtonEnabled = true;
        }

        // json読み込み
        val json = assets.open("tenpo.json").reader(charset = Charsets.UTF_8).use { it.readText() }
        val storeList: StoreList? = StoreList.adapter().fromJson(json)
        val list = storeList?.list ?: listOf()
        // TODO 非同期で処理してマッピング
        val sublist = list.subList(0,100)

        val geoCoder = Geocoder(this, Locale.getDefault());
        sublist.forEach {
            val start = System.currentTimeMillis()
            val hoge = geoCoder.getFromLocationName(it.address, 1)
            System.out.println(System.currentTimeMillis() - start)
            // 住所から位置の変換に失敗した場合はスキップ
            if (hoge.size < 1) {
                System.out.println(it.name)
                return@forEach
            }
            val address = hoge[0]
            val latitude = address.latitude
            val longitude = address.longitude
            val position = LatLng(latitude, longitude)
            googleMap.addMarker(MarkerOptions().position(position).title(it.name))
        }

        // デフォルト表示
        val position = LatLng(35.6489301, 139.6926504)
        googleMap.addMarker(MarkerOptions().position(position).title("中目黒店"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(position))

        // 現在位置
        googleMap.setOnMyLocationButtonClickListener {
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val location: Location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(location.latitude, location.longitude)))
            false
        }
    }
}
