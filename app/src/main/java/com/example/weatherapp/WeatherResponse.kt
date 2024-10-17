package com.example.weatherapp

data class WeatherResponse(
    val main: Main,
    val name: String,
    val weather: List<Weather>
)

data class Main(
    val temp: Double
)

data class Weather(
    val description: String
)
