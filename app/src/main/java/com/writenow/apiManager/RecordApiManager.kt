package com.writenow.apiManager

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.writenow.api.RecordService
import com.writenow.event.NumberErrorEvent
import com.writenow.model.RecordModel
import com.writenow.model.ResultModel
import org.greenrobot.eventbus.EventBus
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class RecordApiManager {
    private var retrofit: Retrofit? = null
    private var retrofitService: RecordService? = null
    val _resultLivedata: MutableLiveData<String> = MutableLiveData()
    val resultLivedata: LiveData<String>
        get() = _resultLivedata

    companion object {  // DCL 적용한 싱글톤 구현
        var instance: RecordApiManager? = null
        fun getInstance(context: Context?): RecordApiManager? {
            if (instance == null) {
                @Synchronized
                if (instance == null)
                    instance = RecordApiManager()
            }
            return instance
        }
    }

    init {
        retrofit = Retrofit.Builder()
            .baseUrl("http://3.34.136.127:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitService = retrofit?.create(RecordService::class.java)
    }

    fun postRecord(recordData: RecordModel) {
        Log.d("postRecord_request", recordData.toString())
        val resultData: Call<ResultModel>? = retrofitService?.postRecord(recordData)
        resultData?.enqueue(object : Callback<ResultModel> {
            override fun onResponse(
                call: Call<ResultModel>,
                response: Response<ResultModel>
            ) {
                if (response.isSuccessful) {
                    val result: ResultModel = response.body()!!
                    Log.d("postRecord_result", result.toString())

                    if (_resultLivedata.value==null)
                        _resultLivedata.postValue(result.predicted_alphabet)
                    else
                        _resultLivedata.postValue(_resultLivedata.value+result.predicted_alphabet)
                } else {
                    EventBus.getDefault().post(NumberErrorEvent("CODE", response.code()))
                    Log.d("postRecord_result", "FAIL CODE: "+response.code())
                }
            }

            override fun onFailure(call: Call<ResultModel>, t: Throwable) {
                t.printStackTrace()
                EventBus.getDefault().post(NumberErrorEvent("FAIL", -1))
                Log.d("postRecord_result", "API FAIL")
            }
        })
    }
}