package com.animesh.android.creatures.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.appcompat.app.AppCompatActivity
import com.animesh.android.creatures.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    setupNavigation()
    setupViewPager()
  }

  private fun setupNavigation() {
    navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
  }

  private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
    when (item.itemId) {
      R.id.navigation_all -> {
        title = getString(R.string.app_name)
        viewPager.setCurrentItem(0, false)
        return@OnNavigationItemSelectedListener true
      }
      R.id.navigation_favorites -> {
        title = getString(R.string.title_favorites)
        viewPager.setCurrentItem(1, false)
        return@OnNavigationItemSelectedListener true
      }
    }
    false
  }

  private fun setupViewPager() {
    viewPager.adapter = object : androidx.fragment.app.FragmentPagerAdapter(supportFragmentManager) {
      override fun getItem(position: Int): androidx.fragment.app.Fragment? {
        when (position) {
          0 -> return AllFragment.newInstance()
          1 -> return FavoritesFragment.newInstance()
        }
        return null
      }

      override fun getCount() = 2
    }
  }
}
