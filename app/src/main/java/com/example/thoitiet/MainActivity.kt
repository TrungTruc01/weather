package com.example.thoitiet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.thoitiet.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navView = binding.navView

        // Thay thế ForecastFragment khi ứng dụng khởi động
        if (savedInstanceState == null) {
            replaceFragment(ForecastFragment()) // Thay thế ForecastFragment mặc định
        }

        // Đặt sự kiện khi người dùng chọn mục trong Navigation Drawer
        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_forecast -> {
                    // Thay thế fragment với nội dung Dự báo
                    replaceFragment(ForecastFragment())
                }
                R.id.nav_radar -> {
                    // Thay thế fragment với nội dung Radar
                    replaceFragment(RadarFragment())
                }
                R.id.nav_location -> {
                    // Thay thế fragment với nội dung Vị trí
                    replaceFragment(LocationFragment())
                }
            }
            drawerLayout.closeDrawers() // Đóng drawer sau khi chọn
            true
        }
    }

    // Hàm thay thế Fragment
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
