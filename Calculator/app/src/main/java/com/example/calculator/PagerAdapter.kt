package com.example.calculator

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerAdapter(
    fragmentManager: FragmentManager, private val listFragments:
    ArrayList<Fragment>
): FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return listFragments.get(position)
    }

    override fun getCount(): Int {
        return listFragments.size
    }
}