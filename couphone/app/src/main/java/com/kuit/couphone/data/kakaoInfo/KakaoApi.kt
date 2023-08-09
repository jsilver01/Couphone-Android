
import com.kuit.couphone.data.kakaoInfo.AddressInfo
import com.kuit.couphone.data.kakaoInfo.GPSInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoAPI {
    @GET("v2/local/search/address.json")    // Keyword.json의 정보를 받아옴
    fun getSearchKeyword(
        @Header("Authorization") key: String,     // 카카오 API 인증키 [필수]
        @Query("query") query: String             // 검색을 원하는 질의어 [필수]
        // 매개변수 추가 가능
        // @Query("category_group_code") category: String

    ): Call<AddressInfo>    // 받아온 정보가 ResultSearchKeyword 클래스의 구조로 담김

    @GET("/v2/local/geo/transcoord.json")
    fun getWTMGPS(
        @Header("Authorization") key : String,
        @Query("x") x: Double,
        @Query("y") y: Double,
        @Query("input_coord") input_coord: String,
        @Query("output_coord") output_coord: String,
    ) : Call<GPSInfo>
}