package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import android.provider.Settings.Secure



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pInfo = this.packageManager.getPackageInfo(packageName, 0)
        val version = pInfo.versionName

        val appVersionTxtView = findViewById<TextView>(R.id.appVersionTxtView)
        appVersionTxtView.text = version

        val deviceIdTxtView = findViewById<TextView>(R.id.deviceIdTxtView)
        deviceIdTxtView.text = Secure.getString(contentResolver, Secure.ANDROID_ID)
    }
}
