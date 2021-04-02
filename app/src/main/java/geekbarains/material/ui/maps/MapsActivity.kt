package geekbarains.material.ui.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import geekbarains.material.R
import kotlinx.android.synthetic.main.activity_maps.*
import kotlin.properties.Delegates

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object{
        const val LAT = "lat"
        const val LON = "lon"

        fun start(context: Context, lat:Float?, lon:Float?) {
            val intent  = Intent(context, MapsActivity::class.java)
                    intent.putExtra(LAT, lat)
                    intent.putExtra(LON, lon)
            context.startActivity(intent)
        }
    }
    /** The Google Map object.  */
    private var mMap: GoogleMap? = null
    private var lat by Delegates.notNull<Float>()
    private var lon by Delegates.notNull<Float>()

    /** Location manager  */
    private var mLocManager: LocationManager? = null

    /**
     * {@inheritDoc}
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

       lat =   intent.getFloatExtra(LAT, 0f)
       lon =   intent.getFloatExtra(LON, 0f)

        //поддержка экшенбара
        setSupportActionBar(toolbar_maps)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        //ставим слушатель нажатия на стрелку Назад в тулбаре
        toolbar_maps.setNavigationOnClickListener {
            onBackPressed()
        }

        //текстовое поле в тулбаре
        with(toolbar_maps.findViewById<TextView>(R.id.maps_title)){
            textSize = 16f
            setTextColor(Color.WHITE)
            text = "" /*context.getString(R.string.The_universe_in_the_palm)*/
        }

        // Location manager instance
        mLocManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Receive Google Map object
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available. This callback is triggered when the map is ready to be
     * used.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Enable the my-location layer in the map
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), 100)
        } else {
            mMap!!.isMyLocationEnabled = true
            // Disable my-location button
            mMap!!.uiSettings.isMyLocationButtonEnabled = false
            // Enable zoom controls
            mMap!!.uiSettings.isZoomControlsEnabled = true

            //переводим координаты в цель LatLng
            val target = LatLng(lat.toDouble(), lon.toDouble())
            // Defines a camera move. An object of this type can be used to modify a map's camera
            // by calling moveCamera()
            val camUpdate = CameraUpdateFactory.newLatLngZoom(target, 1f)

            // Move camera to point with Animation
            mMap!!.animateCamera(camUpdate)

            //ставим цедевую точку на карте
            mMap!!.addMarker(MarkerOptions().position(target))

            //выбираем вид со спутника
            mMap!!.mapType = GoogleMap.MAP_TYPE_SATELLITE
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == 100) {
            val permissionsGranted =
                (grantResults.size > 1 && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            if (permissionsGranted) recreate()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_maps, menu)
        return true
    }
    /**
     * {@inheritDoc}
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_map_mode_satellite -> {
                mMap!!.mapType = GoogleMap.MAP_TYPE_SATELLITE
              return  true
            }
            R.id.menu_map_mode_terrain ->{
                mMap!!.mapType = GoogleMap.MAP_TYPE_TERRAIN
                return true
            }
            R.id.menu_map_mode_default ->{
                mMap!!.mapType = GoogleMap.MAP_TYPE_NORMAL
                return true
            }
            R.id.menu_map_point_new ->{
                val map_taget = LatLng(lat.toDouble(), lon.toDouble())
                mMap!!.addMarker(MarkerOptions().position(map_taget))
                moveCamera(map_taget, 3f)
                return true
            }
            R.id.menu_map_me_here ->{
                meHere(14f)
            }

            R.id.menu_map_location  ->{
                meHere(5f)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun meHere(zoom:Float) {
        if (mMap!!.isMyLocationEnabled) {
            // Get last know location
            @SuppressLint("MissingPermission")
            val loc =
                mLocManager!!.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)

            loc?.let {
                // Create LatLng object for Maps
                val target = LatLng(loc.latitude, loc.longitude)
                // Defines a camera move. An object of this type can be used to modify a map's camera
                // by calling moveCamera()
                val camUpdate = CameraUpdateFactory.newLatLngZoom(target, zoom)
                // Move camera to point with Animation
                mMap!!.animateCamera(camUpdate)
            }
        }
    }

    /**
     * Move Map's camera to specified target.
     */
    private fun moveCamera(target: LatLng?, zoom: Float) {
        if (target == null || zoom < 1) return
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(target, zoom))
    }
}
