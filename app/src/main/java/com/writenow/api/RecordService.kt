package com.example.writenow_watch.api

import com.example.writenow.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RecordService {
    @POST("/process_audio/")
    fun postRecord(@Body recordData: RecordModel): Call<ResultModel>
}