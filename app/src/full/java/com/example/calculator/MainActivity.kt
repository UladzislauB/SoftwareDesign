package com.example.calculator

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager


class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val orientation = this.resources.configuration.orientation

        var fragmentsList = ArrayList<Fragment>()
        fragmentsList.add(BasicModeFragment())
        fragmentsList.add(ScientificModeFragment())

        if(orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            viewPager = findViewById(R.id.pager)
            viewPager.adapter = PagerAdapter(supportFragmentManager, fragmentsList)
        }

    }
}
