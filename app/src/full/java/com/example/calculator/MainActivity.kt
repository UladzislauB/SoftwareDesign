package com.example.calculator

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager




interface DataInterface {
    fun setResult(needClear: Boolean)
}


class MainActivity : AppCompatActivity(), DataInterface {
    private lateinit var fragmentsList: ArrayList<Fragment>

    override fun setResult(needClear: Boolean) {

        (fragmentsList[1] as ScientificModeFragment).setResult(needClear)
        (fragmentsList[0] as BasicModeFragment).setResult(needClear)
    }


    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_main)



        val orientation = this.resources.configuration.orientation

        fragmentsList = ArrayList()
        fragmentsList.add(BasicModeFragment())
        fragmentsList.add(ScientificModeFragment())


        if(orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            viewPager = findViewById(R.id.pager)
            viewPager.adapter = PagerAdapter(supportFragmentManager, fragmentsList)
        }
        else{
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.mode_basic, fragmentsList[0])
                .add(R.id.mode_scientific, fragmentsList[1]).commit()
        }
    }


}
