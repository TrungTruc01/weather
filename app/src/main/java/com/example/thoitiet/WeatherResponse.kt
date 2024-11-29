package com.example.thoitiet

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val main: Main,
    val rain: Rain?,
    val clouds: Clouds,
    val wind: Wind,
    val weather: List<Weather> // Thêm trường weather để lưu mô tả thời tiết
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_max: Double,
    val temp_min: Double
)

data class Rain(
    @SerializedName("1h") val `1h`: Double? = null // Lượng mưa trong 1 giờ, nếu có
)

data class Clouds(
    val all: Int // Tỉ lệ mây, ví dụ: 100 là mây bao phủ hoàn toàn
)

data class Wind(
    val speed: Double // Tốc độ gió, đơn vị km/h
)

data class Weather(
    val description: String, // Mô tả thời tiết, ví dụ: "clear sky", "few clouds", v.v.
    val icon: String, // Mã icon thời tiết (ví dụ: "01d", "02n", v.v.)
    val main: String // Thêm trường main để lưu thông tin chung về thời tiết như "Clear", "Clouds", "Rain", v.v.
)

