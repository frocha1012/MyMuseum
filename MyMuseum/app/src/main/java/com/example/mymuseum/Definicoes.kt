package com.example.mymuseum

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.mymuseum.base.BaseCompatActivity
import com.example.mymuseum.utils.PreferencesUtil
import kotlinx.android.synthetic.main.activity_definicoes.*

class Definicoes : BaseCompatActivity() {

    private val PERM_REQUEST_CODE = 1

    private val GPS_REQUEST_CODE = 2

    private val GPS_REQUEST_CODE1 = 3

    private var isGps by PreferencesUtil("isGps", false)

    private var isDark by PreferencesUtil("isDark", false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_definicoes)

        initView()

        init()

    }

    private fun initView() {

        if (isGps) {
            if (checkGPSIsOpen()) {
                switch5.isChecked = true
            } else {
                switch5.isChecked = false
            }
        } else {
            switch5.isChecked = false
        }

        if (isDark) {
            switch4.isChecked = true
        } else {
            switch4.isChecked = false
        }

    }

    private fun init() {

        switch5.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                requestPermission()
            } else {
                if (checkGPSIsOpen()) {
                    AlertDialog.Builder(this)
                        .setTitle(R.string.notifyTitle)
                        .setMessage(R.string.gpsNotifyMsg)
                        .setNegativeButton(R.string.cancel,
                            object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface?, which: Int) {
                                    isGps = true
                                    switch5.isChecked = true
                                }
                            })
                        .setPositiveButton(R.string.setting,
                            object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface?, which: Int) {
                                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                                    startActivityForResult(intent, GPS_REQUEST_CODE1)
                                }

                            })

                        .setCancelable(false)
                        .show()
                } else {
                    isGps = false
                    switch5.isChecked = false
                }

            }
        }

        switch4.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                isDark = true
                delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
            } else {
                isDark = false
                delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
            }
        }
    }

    @TargetApi(23)
    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissions = ArrayList<String>()
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
            if (permissions.size > 0) {
                requestPermissions(permissions.toTypedArray(), PERM_REQUEST_CODE)
            }
            openGpsSetting()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERM_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGpsSetting()
        } else {
            isGps = false
            switch5.isChecked = false
            Toast.makeText(this, "no permissions", Toast.LENGTH_LONG).show()
        }
    }

    private fun openGpsSetting() {

        if (checkGPSIsOpen()) {
            //initLocation()
            isGps = true
            switch5.isChecked = true
        } else {
            AlertDialog.Builder(this)
                .setTitle(R.string.notifyTitle)
                .setMessage(R.string.gpsNotifyMsg)
                .setNegativeButton(R.string.cancel,
                    object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            isGps = false
                            switch5.isChecked = false
                            finish()
                        }
                    })
                .setPositiveButton(R.string.setting,
                    object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                            startActivityForResult(intent, GPS_REQUEST_CODE)
                        }

                    })

                .setCancelable(false)
                .show()

        }

    }

    private fun checkGPSIsOpen(): Boolean {
        var isOpen = false
        val locationManager = this
            .getSystemService(Context.LOCATION_SERVICE) as LocationManager
        isOpen = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)
        return isOpen;
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GPS_REQUEST_CODE) {
            openGpsSetting()
        }
        if (requestCode == GPS_REQUEST_CODE1) {
            if (!checkGPSIsOpen()) {
                isGps = false
                switch5.isChecked = false
            } else {
                isGps = true
                switch5.isChecked = true
            }
        }
    }

}
