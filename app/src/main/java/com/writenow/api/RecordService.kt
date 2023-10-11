package com.writenow.api

import com.writenow.model.RecordModel
import com.writenow.model.ResultModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RecordService {
    @POST("/process_audio/")
    fun postRecord(@Body recordData: RecordModel): Call<ResultModel>
}