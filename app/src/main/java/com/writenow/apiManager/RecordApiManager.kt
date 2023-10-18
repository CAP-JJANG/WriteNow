package com.writenow.apiManager

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.writenow.api.RecordService
import com.writenow.model.GetTestModel
import com.writenow.model.RecordModel
import com.writenow.model.ResultModel
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration
import java.time.LocalDateTime

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
        // https://jsonplaceholder.typicode.com/posts
        // http://15.164.224.196:8000
        retrofit = Retrofit.Builder()
            .baseUrl("http://43.201.21.103:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitService = retrofit?.create(RecordService::class.java)
    }

    fun getTest() {
        val resultData: Call<ArrayList<GetTestModel>>? = retrofitService?.getTest()
        resultData?.enqueue(object : Callback<ArrayList<GetTestModel>> {
            override fun onResponse(
                call: Call<ArrayList<GetTestModel>>,
                response: Response<ArrayList<GetTestModel>>
            ) {
                if (response.isSuccessful) {
                    val result: ArrayList<GetTestModel> = response.body()!!
                    Log.d("resultt", result[0].toString())
                } else {
                    Log.d("resultt_실패", response.code().toString())
                }
            }

            override fun onFailure(call: Call<ArrayList<GetTestModel>>, t: Throwable) {
                t.printStackTrace()
                Log.d("resultt_통신 실패", resultData.toString())
            }
        })
    }

    fun postRecord(recordData: RecordModel, previous:LocalDateTime) {
        Log.d("resultt_request", recordData.toString())
        val resultData: Call<ResultModel>? = retrofitService?.postRecord(recordData)
        resultData?.enqueue(object : Callback<ResultModel> {
            override fun onResponse(
                call: Call<ResultModel>,
                response: Response<ResultModel>
            ) {
                if (response.isSuccessful) {
                    val result: ResultModel = response.body()!!
                    Log.d("resultt", resultLivedata.toString())

                    if (_resultLivedata.value==null)
                        _resultLivedata.postValue(result.predicted_alphabet)
                    else
                        _resultLivedata.postValue(_resultLivedata.value+result.predicted_alphabet)

                    val now = LocalDateTime.now()
                    val duration = Duration.between(previous, now)
                    val hours = duration.toHours()
                    val minutes = duration.toMinutes() % 60
                    val seconds = duration.seconds % 60
                } else {
                    Log.d("resultt", "실패")
                }
            }

            override fun onFailure(call: Call<ResultModel>, t: Throwable) {
                t.printStackTrace()
                Log.d("resultt_통신실패", resultData.toString())
            }
        })
    }
}