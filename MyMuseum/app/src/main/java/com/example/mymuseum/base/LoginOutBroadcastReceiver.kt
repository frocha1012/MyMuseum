package com.example.mymuseum.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.mymuseum.ui.login.LoginActivity
import com.example.mymuseum.utils.ActivityCollector.finishAll

class LoginOutBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        finishAll()
        val intent1 = Intent(context, LoginActivity::class.java)
        context.startActivity(intent1)
    }
}