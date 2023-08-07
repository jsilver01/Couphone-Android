package com.kuit.couphone

import KakaoAPI
import StoreAdapter
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.kuit.couphone.data.StoreInfo
import com.kuit.couphone.data.kakaoInfo.AddressInfo
import com.kuit.couphone.databinding.FragmentMyLocationBinding
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapReverseGeoCoder
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class MyLocationFragment : Fragment(),MapView.MapViewEventListener {
    companion object {
        const val BASE_URL = "https://dapi.kakao.com/"
        const val API_KEY = "KakaoAK 079ad8dac5fa1bc11ec4fc2f07e56c59"  // REST API 키
    }
    var isMarkerAdded = false
    lateinit var binding: FragmentMyLocationBinding
    private val ACCESS_FINE_LOCATION = 1000
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var mContext: Context
    var adapter : StoreAdapter?= null
    var storeList = ArrayList<StoreInfo>()
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
        initDummyData()
        adapter = StoreAdapter(storeList)
        binding.storeListRv.adapter = adapter
        binding.storeListRv.layoutManager = LinearLayoutManager(context)
        adapter!!.setOnItemClickListener(object : StoreAdapter.OnItemClickListener{
            override fun onItemClick(itemList: StoreInfo) {
                val intent = Intent(requireContext(), InformationActivity::class.java)
                startActivity(intent)
            }

        })
        binding.zoomin.setOnClickListener {
            binding.mapView.zoomIn(true)
        }
        binding.zoomout.setOnClickListener {
            binding.mapView.zoomOut(true)
        }
        binding.mygps.setOnClickListener{
            move_to_my_gps()
        }
        binding.searchFl.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, SearchMapFragment()).commit()
        }
        binding.mapView.setMapViewEventListener(this)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
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
                binding.mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(location.latitude,location.longitude),true)
                val reverseGeoCoder = MapReverseGeoCoder(
                    "4d8bc2574df1b7f508727581b2d59c7e",
                    MapPoint.mapPointWithGeoCoord(location.latitude,location.longitude),
                    object : MapReverseGeoCoder.ReverseGeoCodingResultListener {
                        override fun onReverseGeoCoderFoundAddress(
                            mapReverseGeoCoder: MapReverseGeoCoder,
                            s: String
                        ) {
                            convertAddress(s)
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
                            convertAddress(s)
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

    override fun onMapViewInitialized(p0: MapView?) {

    }

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
    }

    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
        binding.mapView.removeAllPOIItems()
        val marker = MapPOIItem()
        marker.itemName = "마커 이름"
        marker.tag = 0
        marker.mapPoint = MapPoint.mapPointWithGeoCoord(p1?.mapPointGeoCoord!!.latitude, p1?.mapPointGeoCoord!!.longitude)
        Log.d("testlatitue",p1?.mapPointGeoCoord!!.latitude.toString())
        marker.markerType = MapPOIItem.MarkerType.BluePin        // 마커 모양 (커스텀)
        marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin  // 클릭 시 마커 모양 (커스텀)
        // 지도에 마커 추가
        binding.mapView.addPOIItem(marker)
        isMarkerAdded = true // 마커 추가 상태로 변경
        //선택한 위치로 지도 중심 변경
        binding.mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(p1?.mapPointGeoCoord!!.latitude, p1?.mapPointGeoCoord!!.longitude),true)
        val reverseGeoCoder = MapReverseGeoCoder(
            "4d8bc2574df1b7f508727581b2d59c7e",
            MapPoint.mapPointWithGeoCoord(p1?.mapPointGeoCoord!!.latitude, p1?.mapPointGeoCoord!!.longitude),
            object : MapReverseGeoCoder.ReverseGeoCodingResultListener {
                override fun onReverseGeoCoderFoundAddress(
                    mapReverseGeoCoder: MapReverseGeoCoder,
                    s: String
                ) {
                    convertAddress(s)
                }

                override fun onReverseGeoCoderFailedToFindAddress(mapReverseGeoCoder: MapReverseGeoCoder) {
                    binding.cafeNameTv.text = ("address not found")
                }
            },
            requireActivity()
        )
        reverseGeoCoder.startFindingAddress()

    }

    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {
    }

    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {
    }

    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {
    }

    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
    }

    private fun convertAddress(keyword: String) {
        val retrofit = Retrofit.Builder()   // Retrofit 구성
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(KakaoAPI::class.java)   // 통신 인터페이스를 객체로 생성
        val call = api.getSearchKeyword(API_KEY, keyword)   // 검색 조건 입력

        // API 서버에 요청
        call.enqueue(object: Callback<AddressInfo> {
            override fun onResponse(
                call: Call<AddressInfo>,
                response: Response<AddressInfo>
            ) {
                // 통신 성공 (검색 결과는 response.body()에 담겨있음)
                Log.d("Test", "Raw: ${response.raw()}")
                Log.d("Test", "Body: ${response.body()}")
                if(response.body()==null){
                    binding.cafeNameTv.text = keyword
                }
                else {
                    if(response.body()!!.documents[0].road_address != null) {
                        if (response.body()!!.documents[0].road_address.building_name == "") {
                            binding.cafeNameTv.text = response.body()!!.documents[0].address.address_name
                        } else {
                            var address =
                                response.body()!!.documents[0].road_address.region_3depth_name + " " + response.body()!!.documents[0].road_address.building_name
                            binding.cafeNameTv.text = address
                        }
                    }else{
                        binding.cafeNameTv.text = keyword
                    }
                }
            }

            override fun onFailure(call: Call<AddressInfo>, t: Throwable) {
                // 통신 실패
                Log.w("MainActivity", "통신 실패: ${t.message}")
            }
        })
    }
    private fun initDummyData() {
        storeList.add(StoreInfo("test1", "test1111111"))
        storeList.add(StoreInfo("test2", "test22222222222"))
        storeList.add(StoreInfo("test3", "test333333333333333333"))
        storeList.add(StoreInfo("test4", "test4444444444444444444"))
    }
}