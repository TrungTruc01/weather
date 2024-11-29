package com.example.thoitiet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.example.thoitiet.databinding.FragmentForecastBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastFragment : Fragment(R.layout.fragment_forecast) {

    private lateinit var binding: FragmentForecastBinding
    private lateinit var weatherAnimationView: LottieAnimationView

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentForecastBinding.bind(view)
        weatherAnimationView = binding.weatherAnimation // Lottie view trong layout

        val defaultCity = "Phu Hoa"
        binding.cityName.text = "Vị trí của tôi"

        // Lấy dữ liệu thời tiết cho vị trí mặc định
        fetchWeather(defaultCity)
    }

    private fun fetchWeather(city: String) {
        val apiKey = "ba6c2ce56e4d9121d546d0fc5a538461"
        val call = RetrofitInstance.retrofitService.getWeather(city, apiKey)

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { weather -> updateUI(weather) }
                        ?: Toast.makeText(requireContext(), "Dữ liệu thời tiết không hợp lệ.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Không thể lấy dữ liệu thời tiết.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Lỗi kết nối: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(weather: WeatherResponse) {
        binding.textTemperature.text = "${weather.main.temp.toInt()}°"
        binding.feelsLike.text = "${weather.main.feels_like.toInt()}°"
        binding.tempMax.text = "Cao: ${weather.main.temp_max.toInt()}°"
        binding.tempMin.text = "Thấp: ${weather.main.temp_min.toInt()}°"
        val rainAmount = weather.rain?.`1h` ?: 0.0
        binding.rainAmount.text = "$rainAmount mm"
        binding.precipitationChance.text = "${weather.clouds.all}%"
        binding.windSpeed.text = "${weather.wind.speed} km/h"

        val weatherDescription = weather.weather.firstOrNull()?.description ?: "Không có mô tả"
        val translatedDescription = translateWeatherDescription(weatherDescription)
        binding.weatherDescription.text = "$translatedDescription"

        val weatherCondition = weather.weather.firstOrNull()?.main?.lowercase() ?: "clear"

        when (weatherCondition) {
            "clear" -> weatherAnimationView.setAnimation("muanang.json")
            "rain" -> weatherAnimationView.setAnimation("mua.json")
            "clouds" -> weatherAnimationView.setAnimation("samchop.json")
            "thunderstorm" -> weatherAnimationView.setAnimation("nhieugio.json")
            "fog" -> weatherAnimationView.setAnimation("suongmu.json")
            "storm" -> weatherAnimationView.setAnimation("nhieumay.json")
            "windy" -> weatherAnimationView.setAnimation("suongmu.json")
            else -> weatherAnimationView.setAnimation("muanang.json")
        }
        weatherAnimationView.playAnimation()
    }

    private fun translateWeatherDescription(description: String): String {
        return when (description.lowercase()) {
            "clear sky" -> "Trời quang"
            "few clouds" -> "Một vài đám mây"
            "scattered clouds" -> "Mây rải rác"
            "broken clouds" -> "Mây che phủ"
            "shower rain" -> "Mưa rào"
            "rain" -> "Mưa"
            "thunderstorm" -> "Giông bão"
            "mist" -> "Sương mù"
            else -> description
        }
    }
}
