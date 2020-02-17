package com.example.yetanotherfeed

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.yetanotherfeed.network.ConnectivityReceiver
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("Registered")
class MainActivity : AppCompatActivity(),
    ConnectivityReceiver.ConnectivityReceiverListener {

    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        registerReceiver(
            ConnectivityReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }


    private fun showMessage(isConnected: Boolean) {

        if (!isConnected) {
            val messageToUser = "You are offline now"
            snackbar = Snackbar.make(
                findViewById(R.id.nav_host_fragment),
                messageToUser,
                Snackbar.LENGTH_INDEFINITE
            )
            snackbar?.show()
        } else {
            if (snackbar != null) {
                snackbar?.dismiss()
                val messageToUser = "Online"
                snackbar = Snackbar.make(
                    findViewById(R.id.nav_host_fragment),
                    messageToUser,
                    Snackbar.LENGTH_SHORT
                )
                snackbar?.show()
            }
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onResume() {
        super.onResume()

        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showMessage(isConnected)
    }
}
