package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pInfo = this.getPackageManager().getPackageInfo(packageName, 0)
        val version = pInfo.versionName

        val appVersionTxtView = findViewById<TextView>(R.id.appVersionTxtView) as TextView
        appVersionTxtView.setOnClickListener{
            appVersionTxtView.text = version
        }
    }
}
