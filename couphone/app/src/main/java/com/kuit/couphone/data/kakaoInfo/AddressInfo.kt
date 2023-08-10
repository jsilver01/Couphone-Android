package com.kuit.couphone.data.kakaoInfo

data class AddressInfo(
    var meta: Meta,                // 장소 메타데이터
    var documents: List<Address>          // 검색 결과
)

data class Meta(
    var total_count: Int,               // 검색어에 검색된 문서 수
    var pageable_count: Int,            // total_count 중 노출 가능 문서 수, 최대 45 (API에서 최대 45개 정보만 제공)
    var is_end: Boolean,                // 현재 페이지가 마지막 페이지인지 여부, 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음
)

data class Address(
    var addres_name: String,
    var address_type: String,
    var x_lat: String,
    var y_long: String,
    var address: AddressGround,
    var road_address: road_address,

    )

data class AddressGround(
    var address_name: String,    //전체 지번 주소
    var region_1depth_name: String,//	지역 1 Depth, 시도 단위
    var region_2depth_name: String,//	지역 2 Depth, 구 단위
    var region_3depth_name: String,    //지역 3 Depth, 동 단위
    var region_3depth_h_name: String,//	지역 3 Depth, 행정동 명칭
    var h_code: String,//	행정 코드
    var b_code: String,//	법정 코드
    var mountain_yn: String,    //산 여부, Y 또는 N
    var main_address_no: String,    //지번 주번지
    var sub_address_no: String,    //지번 부번지, 없을 경우 빈 문자열("") 반환
    var x: String,//	X 좌표값, 경위도인 경우 경도(longitude)
    var y: String,    //Y 좌표값, 경위도인 경우 위도(latitude)
)

data class road_address(
    var address_name: String,    //전체 지번 주소
    var region_1depth_name: String,//	지역 1 Depth, 시도 단위
    var region_2depth_name: String,//	지역 2 Depth, 구 단위
    var region_3depth_name: String,    //지역 3 Depth, 동 단위
    var road_name: String,//	지역 3 Depth, 행정동 명칭
    var underground_yn: String,//	행정 코드
    var main_building_no: String,//	법정 코드
    var sub_building_no: String,    //산 여부, Y 또는 N
    var building_name: String,    //지번 주번지
    var zone_no: String,    //지번 부번지, 없을 경우 빈 문자열("") 반환
    var x: String,//	X 좌표값, 경위도인 경우 경도(longitude)
    var y: String,    //Y 좌표값, 경위도인 경우 위도(latitude)
)
data class GPSInfo(
    var meta: GPSMeta,                // 장소 메타데이터
    var documents: List<GPS>          // 검색 결과
)
data class GPSMeta(
    var total_count : String
)

data class GPS(
    var x : Double,
    var y : Double
)
