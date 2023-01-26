package com.example.mymuseum.base

import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mymuseum.utils.ActivityCollector.addActivity
import com.example.mymuseum.utils.ActivityCollector.removeActivity

open class BaseCompatActivity : AppCompatActivity() {

    protected var locallReceiver: LoginOutBroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addActivity(this)
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.gesoft.admin.loginout")
        locallReceiver = LoginOutBroadcastReceiver()
        registerReceiver(locallReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(locallReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeActivity(this)
    }
}