package com.writenow.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.provider.ContactsContract
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.writenow.MyApplication.Companion.prefs
import com.writenow.model.RecordModel
import com.writenow.apiManager.RecordApiManager
import com.writenow.R
import com.writenow.base.BaseFragment
import com.writenow.databinding.FragmentRecordBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.time.LocalDateTime
import java.util.Date


class RecordFragment : BaseFragment<FragmentRecordBinding>(R.layout.fragment_record) {
    private var mediaRecorder: MediaRecorder? = null
    private var isRecording: Boolean = false
    private var fileName = ""
    private var filePath = ""
    private val apiManager = RecordApiManager.getInstance(context)

    private lateinit var name:String
    private lateinit var phone:String

    private var requestLauncher:ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ it ->
            if (it.resultCode == Activity.RESULT_OK) {
                // 누른 주소록 정보 받아오기
                val oneCursor = requireContext().contentResolver.query(
                    it.data!!.data!!,
                    arrayOf(
                        ContactsContract.PhoneLookup.DISPLAY_NAME
                    )
                    ,null,null,null
                )
                // 주소록 상세 정보 받아오기
                val cursor = requireContext().contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    arrayOf(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                    )
                    ,null, null,null
                )

                oneCursor?.use{
                    if(it.moveToFirst())
                        name = it.getString(0)
                }

                cursor?.use {
                    while (cursor!=null){
                        if (it.moveToNext()) {
                            if (name==cursor.getString(0)){
                                phone = cursor.getString(1)
                                break
                            }
                        }
                    }
                }
                prefs.setString("sos", phone.toString())
                val toast = Toast.makeText(context, name+"님을 \n긴급 연락처로 지정했습니다.", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL)
                toast.show()
            }
        }

    override fun initStartView() {
        super.initStartView()

        apiManager?._resultLivedata?.postValue("")
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun initDataBinding() {
        super.initDataBinding()

        apiManager?.resultLivedata?.observe(viewLifecycleOwner) {
            if (isRecording) {
                binding.tvResultingRecord.text = it
                Toast.makeText(context, "녹음을 시작합니다.", Toast.LENGTH_SHORT).show()
                startRecording()
            }
        }

        binding.btnEdit.setOnClickListener {
            var myIntent:Intent = Intent()

            val permissionListener = object : PermissionListener {

                override fun onPermissionGranted() {
                    myIntent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
                    requestLauncher.launch(myIntent)
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(requireContext(),"[주소록 읽기 권한]이 거부 상태입니다.", Toast.LENGTH_SHORT).show()
                }
            }

            TedPermission.create()
                .setPermissionListener(permissionListener)
                .setDeniedMessage("[주소록 읽기 권한]을 허용해야 sos 연락처 설정이 가능합니다.")
                .setPermissions(android.Manifest.permission.READ_CONTACTS)
                .check()
        }

        // 녹음 시작 버튼
        binding.btnRecord.setOnClickListener {
            // 녹음 진행중이었을시 fragment 전환
            if (isRecording) {
                isRecording = false
                stopRecording()
                // binding.tvResultingRecord.text
                setFragmentResult(
                    "recordResult",
                    bundleOf("result" to "sos")
                )
                navController.navigate(R.id.action_recordFragment_to_showResultFragment)
            } else {
                // 권한 부여 여부
                val isEmpower = ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.RECORD_AUDIO
                ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED

                if (isEmpower) { // 권한 부여 되지 않았을경우
                    empowerRecordAudioAndWriteReadStorage()
                }
                else { // 권한 부여 되었을 경우
                    isRecording = true
                    binding.btnRecord.backgroundTintList = when (isRecording) {
                        false -> ColorStateList.valueOf(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.mainYellow
                            )
                        )
                        true -> ColorStateList.valueOf(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.mainRed
                            )
                        )
                    }

                    if (isRecording) {
                        binding.tvResultingRecord.visibility = View.VISIBLE
                        binding.tvInfoStartRecord.visibility = View.INVISIBLE
                        binding.btnEdit.visibility = View.GONE
                    } else {
                        binding.tvResultingRecord.visibility = View.INVISIBLE
                        binding.tvInfoStartRecord.visibility = View.VISIBLE
                    }

                    startRecording()
                }


            }
        }
    }


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
        fileName = Date().time.toString()+".aac"
        filePath = Environment.getExternalStorageDirectory().absolutePath + "/Download/" + fileName

        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS) // or MediaRecorder.OutputFormat.MPEG_4
            setOutputFile(filePath)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC) // or MediaRecorder.AudioEncoder.DEFAULT
            setAudioSamplingRate(44100) // set the desired sampling rate
            setAudioEncodingBitRate(320000)
            setMaxDuration(1500) // Set the maximum duration to 1.5 seconds

            setOnInfoListener { _, what, _ ->
                if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                    mediaRecorder?.apply {
                        stop()
                        release()

                        // 서버 전송
                        val previous = LocalDateTime.now()
                        val byteArray = mediaRecorderToByteArray(fileName)
                        val recordModel = byteArray?.let { RecordModel(it) }
                        if (recordModel != null) {
                            apiManager?.postRecord(recordModel, previous)
                            Log.d("sendByte", byteArray.toString())
                            Log.d("sendFile", "MediaRecorder: $mediaRecorder, 이름: $fileName")
                        }
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

    private fun mediaRecorderToByteArray(outputFile: String): ByteArray? {
        val file = File(outputFile)
        if (!file.exists()) {
            return null
        }

        val inputStream = FileInputStream(file)
        val buffer = ByteArrayOutputStream()

        inputStream.use { input ->
            buffer.use { output ->
                val data = ByteArray(1024)
                var count: Int
                while (input.read(data).also { count = it } != -1) {
                    output.write(data, 0, count)
                }
                output.flush()
            }
        }
        return buffer.toByteArray()
    }
}