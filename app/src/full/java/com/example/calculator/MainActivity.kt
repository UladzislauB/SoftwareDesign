package com.example.calculator

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager


class MainActivity : BaseActivity() {

    private lateinit var viewPager: ViewPager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val orientation = this.resources.configuration.orientation

        var fragmentsList = ArrayList<Fragment>()
        fragmentsList.add(BasicModeFragment())

        if(orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            viewPager = findViewById(R.id.pager)
            viewPager.adapter = PagerAdapter(supportFragmentManager, fragmentsList)
        }

    }
}
