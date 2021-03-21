package com.example.akiraito.codechallenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.akiraito.codechallenge.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //    private val viewModel by viewmodel<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolBar)

        setNavigationController()
    }

    private fun setNavigationController() {
        val navigationController = findNavController(R.id.nav_host_fragment)
        // navigationに表示するitemはnavigation_menu.xmlに記載
        // itemに使用されてるFragmentはnavigation_fragment_list.xmlに記載
        binding.navView.also {
            // colorPrimaryの色にならないようにnullをセット
            it.itemIconTintList = null
            it.setupWithNavController(navigationController)
        }

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                // 戻るボタン(←)を表示しないFragmentのIDを入れる
                R.id.navigation_home,
                R.id.navigation_favorite,
                R.id.navigation_search
            )
        )

        setupActionBarWithNavController(navigationController, appBarConfiguration)
    }
}