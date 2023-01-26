package com.example.mymuseum.utils

import android.app.Activity
import java.util.*

object ActivityCollector {

    private val actList: MutableList<Activity> = ArrayList()

    @JvmStatic
    fun addActivity(act: Activity) {
        actList.add(act)
    }

    @JvmStatic
    fun removeActivity(act: Activity) {
        actList.remove(act)
    }

    @JvmStatic
    fun finishAll() {
        for (act in actList) {
            if (!act.isFinishing) {
                act.finish()
            }
        }
    }
}