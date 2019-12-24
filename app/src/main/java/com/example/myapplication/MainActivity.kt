package com.example.myapplication

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.telephony.TelephonyManager



class MainActivity : AppCompatActivity() {

    private  var content: String = ""
    private var REQUEST_CODE_READ_PHONE_STATE = 42

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appVersionTxtView = findViewById<TextView>(R.id.appVersionTxtView)
        appVersionTxtView.text = BuildConfig.VERSION_NAME

        val deviceIdTxtView: TextView = findViewById(R.id.deviceIdTxtView)

        val permissionStatus = checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
        if (permissionStatus != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(arrayOf(Manifest.permission.READ_PHONE_STATE),
                REQUEST_CODE_READ_PHONE_STATE)
        }
        else
        {
            val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            content = telephonyManager.getDeviceId()
        }

        deviceIdTxtView.text = content
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode != REQUEST_CODE_READ_PHONE_STATE) return


        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val permissionStatus = checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
        if (permissionStatus == PackageManager.PERMISSION_GRANTED)
        {
            val deviceIdTxtView: TextView = findViewById(R.id.deviceIdTxtView)
            deviceIdTxtView.text = telephonyManager.getDeviceId()
        }
    }
}
