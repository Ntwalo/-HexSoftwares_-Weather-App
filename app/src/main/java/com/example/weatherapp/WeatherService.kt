import com.example.weatherapp.WeatherResponse
import okhttp3.OkHttpClient
import okhttp3.Request
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun fetchWeather(location: String, callback: (String) -> Unit) {
    val apiKey = "827c07ec23df5d5c7d995b1dbcfd08b9"
    val url = "https://api.openweathermap.org/data/2.5/weather?q=$location&units=metric&appid=$apiKey"

    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()

    CoroutineScope(Dispatchers.IO).launch {
        client.newCall(request).execute().use { response ->
            if (response.isSuccessful) {
                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                val adapter = moshi.adapter(WeatherResponse::class.java)
                val weatherResponse = adapter.fromJson(response.body!!.string())

                withContext(Dispatchers.Main) {
                    if (weatherResponse != null) {
                        val result = "Location: ${weatherResponse.name}\n" +
                                "Temperature: ${weatherResponse.main.temp}°C\n" +
                                "Description: ${weatherResponse.weather[0].description}"
                        callback(result)
                    } else {
                        callback("Error parsing response")
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    callback("Failed to retrieve weather data")
                }
            }
        }
    }
}
