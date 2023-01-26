package com.example.mymuseum

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.mymuseum.base.BaseCompatActivity
import com.example.mymuseum.utils.FileUtil
import com.example.mymuseum.utils.PreferencesUtil
import kotlinx.android.synthetic.main.activity_my_area.*
import java.io.File

class MyArea : BaseCompatActivity() {

    private val PHOTO = 5

    private var userName by PreferencesUtil("userName", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_area)

        textView6.text = "Username: "+userName

        imageView5.setOnClickListener {
                checkPermissions()
        }
    }

    private fun checkPermissions() {

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                PHOTO
            )
        } else {
            choosePhoto()
        }
    }

    private fun choosePhoto() {
        val intentToPickPic = Intent(Intent.ACTION_PICK, null)
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        startActivityForResult(intentToPickPic,PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            when (requestCode) {
                PHOTO -> {
                    val uri = data!!.getData()
                    val filePath = FileUtil.getFilePathByUri(this, uri)

                    val file = File(filePath)
                    if (!TextUtils.isEmpty(filePath)) {
                        val requestOptions1 = RequestOptions().skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)

                        Glide.with(this).load(filePath).apply(requestOptions1).into(imageView5)
                    }
                }

            }
        }
    }
}
