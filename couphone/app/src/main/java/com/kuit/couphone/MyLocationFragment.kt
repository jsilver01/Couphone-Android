package com.kuit.couphone

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.*
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.kuit.couphone.databinding.FragmentMyLocationBinding
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapReverseGeoCoder
import net.daum.mf.map.api.MapView
import java.util.*


class MyLocationFragment : Fragment() {
    companion object {
        const val BASE_URL = "https://dapi.kakao.com/"
        const val API_KEY = "4d8bc2574df1b7f508727581b2d59c7e"  // REST API 키
    }
    lateinit var binding: FragmentMyLocationBinding
    private val ACCESS_FINE_LOCATION = 1000
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var mContext: Context
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyLocationBinding.inflate(inflater,container,false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        if (checkLocationService()) {
            permissionCheck()
        } else {
            Toast.makeText(requireContext(), "GPS를 켜주세요", Toast.LENGTH_SHORT).show()
        }
        binding.zoomin.setOnClickListener {
            binding.mapView.zoomIn(true)
        }
        binding.zoomout.setOnClickListener {
            binding.mapView.zoomOut(true)
        }
        binding.mygps.setOnClickListener{
            move_to_my_gps()
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startTracking()
    }

    @SuppressLint("MissingPermission")
    private fun startTracking() {
        binding.mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving  //이 부분
        val lm = requireContext().getSystemService(LOCATION_SERVICE) as LocationManager
        Log.d("testtest",lm.toString())
        val userNowLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        Log.d("testtest",userNowLocation.toString())
        //위도 , 경도
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            // Got last known location. In some rare situations this can be null.
            //var geocoder = Geocoder(requireContext(), Locale.KOREA)
            if (location != null) {
                val uNowPosition = MapPoint.mapPointWithGeoCoord(location.latitude, location.longitude)
                val marker = MapPOIItem()
                marker.itemName = "현 위치"
                marker.mapPoint =uNowPosition
                marker.markerType = MapPOIItem.MarkerType.BluePin
                marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin
                binding.mapView.addPOIItem(marker)
                binding.mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(location.latitude,location.longitude),true)
                val reverseGeoCoder = MapReverseGeoCoder(
                    "4d8bc2574df1b7f508727581b2d59c7e",
                    MapPoint.mapPointWithGeoCoord(location.latitude,location.longitude),
                    object : MapReverseGeoCoder.ReverseGeoCodingResultListener {
                        override fun onReverseGeoCoderFoundAddress(
                            mapReverseGeoCoder: MapReverseGeoCoder,
                            s: String
                        ) {
                            var AddressData = s
                            binding.cafeNameTv.text = AddressData
                        }

                        override fun onReverseGeoCoderFailedToFindAddress(mapReverseGeoCoder: MapReverseGeoCoder) {
                            binding.cafeNameTv.text = ("address not found")
                        }
                    },
                    requireActivity()
                )
                reverseGeoCoder.startFindingAddress()
            }
        }
    }
    @SuppressLint("MissingPermission")
    private fun move_to_my_gps() {
        binding.mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving  //이 부분
        val lm = requireContext().getSystemService(LOCATION_SERVICE) as LocationManager
        Log.d("testtest",lm.toString())
        val userNowLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        Log.d("testtest",userNowLocation.toString())
        //위도 , 경도
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            // Got last known location. In some rare situations this can be null.
            //var geocoder = Geocoder(requireContext(), Locale.KOREA)
            if (location != null) {
                val uNowPosition = MapPoint.mapPointWithGeoCoord(location.latitude, location.longitude)
                binding.mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(location.latitude,location.longitude),true)
                val reverseGeoCoder = MapReverseGeoCoder(
                    "4d8bc2574df1b7f508727581b2d59c7e",
                    MapPoint.mapPointWithGeoCoord(location.latitude,location.longitude),
                    object : MapReverseGeoCoder.ReverseGeoCodingResultListener {
                        override fun onReverseGeoCoderFoundAddress(
                            mapReverseGeoCoder: MapReverseGeoCoder,
                            s: String
                        ) {
                            var AddressData = s
                            binding.cafeNameTv.text = AddressData
                        }

                        override fun onReverseGeoCoderFailedToFindAddress(mapReverseGeoCoder: MapReverseGeoCoder) {
                            binding.cafeNameTv.text = ("address not found")
                        }
                    },
                    requireActivity()
                )
                reverseGeoCoder.startFindingAddress()
            }
        }
    }



    // 위치추적 중지
    private fun stopTracking() {
        binding.mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOff
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTracking()
    }
    private fun permissionCheck() {
        val preference = requireActivity().getPreferences(MODE_PRIVATE)
        val isFirstCheck = preference.getBoolean("isFirstPermissionCheck", true)
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 권한이 없는 상태
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // 권한 거절
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("현재 위치를 확인하시려면 위치 권한을 허용해주세요.")
                builder.setPositiveButton("확인") { dialog, which ->
                    ActivityCompat.requestPermissions(requireActivity(),
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_FINE_LOCATION)
                }
                builder.setNegativeButton("취소") { dialog, which ->

                }
                builder.show()
            } else {
                if (isFirstCheck) {
                    // 최초 권한 요청
                    preference.edit().putBoolean("isFirstPermissionCheck", false).apply()
                    ActivityCompat.requestPermissions(requireActivity(),
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_FINE_LOCATION)
                } else {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setMessage("현재 위치를 확인하시려면 설정에서 위치 권한을 허용해주세요.")
                    builder.setPositiveButton("설정으로 이동") { dialog, which ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:com.kuit.couphone"))
                        startActivity(intent)
                    }
                    builder.setNegativeButton("취소") { dialog, which ->

                    }
                    builder.show()
                }
            }
        } else {

        }

    }

    // 권한 요청
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "위치 권한이 승인되었습니다", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(requireContext(), "위치 권한이 거절되었습니다", Toast.LENGTH_SHORT).show()

            }
        }
    }

    // GPS가 켜져있는지 확인
    private fun checkLocationService(): Boolean {
        val locationManager = requireContext().getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
    fun onReverseGeoCoderFoundAddress(
        mapReverseGeoCoder: MapReverseGeoCoder?,
        addressString: String?
    ) {
        // 주소를 찾은 경우.
    }

    fun onReverseGeoCoderFailedToFindAddress(mapReverseGeoCoder: MapReverseGeoCoder?) {
        // 호출에 실패한 경우.
    }

//    fun getCurrentLocation() {
//        val permissionCheck = ContextCompat
//            .checkSelfPermission(requireContext(), ACCESS_FINE_LOCATION)
//        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
//            val lm = requireContext()
//                .getSystemService(LOCATION_SERVICE) as LocationManager
//
//            try {
//                val userCurLocation = lm
//                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
//                Log.d("testtest","1234")
//                val uLatitude = userCurLocation!!.latitude
//                val uLogitude = userCurLocation.longitude
//                val uCurPosition = MapPoint.mapPointWithGeoCoord(uLatitude, uLogitude)
//                binding.mapView.setMapCenterPoint(uCurPosition, true)
//            } catch (e: java.lang.NullPointerException) {
//                Log.e("LOCATION_ERROR", e.toString())
//
//                ActivityCompat.finishAffinity(requireActivity())
//
//                val intent = Intent(context, MyLocationFragment::class.java)
//                startActivity(intent)
//                System.exit(0)
//            }
//        } else {
//            Toast.makeText(requireContext(),"위치권한 없음",Toast.LENGTH_SHORT).show()
//        }
//    }
}