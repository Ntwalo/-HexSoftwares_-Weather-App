package com.example.weatherapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import fetchWeather

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etLocation: EditText = findViewById(R.id.etLocation)
        val btnGetWeather: Button = findViewById(R.id.btnGetWeather)
        val tvWeatherResult: TextView = findViewById(R.id.tvWeatherResult)

        btnGetWeather.setOnClickListener {
            val location = etLocation.text.toString().trim()
            if (location.isNotEmpty()) {
                fetchWeather(location) { result ->
                    tvWeatherResult.text = result
                }
            } else {
                tvWeatherResult.text = "Please enter a location"
            }
        }
    }
}
