package com.pss.nuvilabtask.data.api


import com.pss.nuvilabtask.data.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("getUltraSrtFcst")
    suspend fun getShortForecast(
        @Query("numOfRows") numOfRows : Int,   // 한 페이지 경과 수
        @Query("pageNo") pageNo : Int,          // 페이지 번호
        @Query("dataType") dataType : String = "JSON",   // 응답 자료 형식
        @Query("base_date") baseDate : String,  // 발표 일자
        @Query("base_time") baseTime : String,  // 발표 시각
        @Query("nx") nx : String,                // 예보지점 X 좌표
        @Query("ny") ny : String,
        ): Response<WeatherResponse>
}