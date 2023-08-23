package com.writenow.ui

import android.app.Activity
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.writenow.R
import com.writenow.base.BaseFragment
import com.writenow.databinding.FragmentRecordBinding
import java.io.IOException
import java.time.LocalDateTime

class RecordFragment : BaseFragment<FragmentRecordBinding>(R.layout.fragment_record) {
    private var mediaRecorder: MediaRecorder? = null
    private var isRecording: Boolean = false
    private var fileName = ""
    private var filePath = ""

    // 레코딩, 파일 읽기 쓰기 권한부여
    private fun empowerRecordAudioAndWriteReadStorage() {
        val permissions = arrayOf(
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        ActivityCompat.requestPermissions(context as Activity, permissions, 0)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun startRecording() {
        fileName = "temp.acc"
        filePath = Environment.getExternalStorageDirectory().absolutePath + "/Download/" + fileName

        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS) // or MediaRecorder.OutputFormat.MPEG_4
            setOutputFile(filePath)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC) // or MediaRecorder.AudioEncoder.DEFAULT
            setAudioSamplingRate(22050) // set the desired sampling rate
            setAudioEncodingBitRate(320000)
            setMaxDuration(1500) // Set the maximum duration to 1.5 seconds

            setOnInfoListener { _, what, _ ->
                if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                    mediaRecorder?.apply {
                        stop()
                        release()

                        mediaRecorder = null
                    }
                }
            }

            try {
                prepare()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            start()
            fileName = filePath
        }
    }

    private fun stopRecording() {
        mediaRecorder = null
    }
}