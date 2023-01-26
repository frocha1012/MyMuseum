package com.example.mymuseum

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mymuseum.base.BaseCompatActivity
import com.example.mymuseum.utils.PreferencesUtil

class Menu : BaseCompatActivity() {

    private var userName by PreferencesUtil("userName", "")

    private var isLogin by PreferencesUtil("isLogin", false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener{
            isLogin = false
            userName = ""
            val intent = Intent("com.gesoft.admin.loginout")
            sendBroadcast(intent)
        }

        val button1 = findViewById<Button>(R.id.button5)
        button1.setOnClickListener{
            val intent = Intent(this,MyArea::class.java)
            startActivity(intent)}

        val button2 = findViewById<Button>(R.id.button3)
        button2.setOnClickListener{
            val intent = Intent(this,Procura::class.java)
            startActivity(intent)}

        val button3 = findViewById<Button>(R.id.button4)
        button3.setOnClickListener{
            val intent = Intent(this,Favoritos::class.java)
            startActivity(intent)}

        val button4 = findViewById<Button>(R.id.button2)
        button4.setOnClickListener{
            val intent = Intent(this,Definicoes::class.java)
            startActivity(intent)}

    }
}
