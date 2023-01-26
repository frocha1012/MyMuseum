package com.example.mymuseum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.mymuseum.base.BaseCompatActivity

class MainActivity : BaseCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.buttonNewsFeed)
        button.setOnClickListener{
            val intent = Intent(this,Menu::class.java)
            startActivity(intent)}
    }

}
