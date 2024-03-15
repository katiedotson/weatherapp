package xyz.katiedotson.weatherapp.weather

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import xyz.katiedotson.weatherapp.R
import xyz.katiedotson.weatherapp.appComponent
import xyz.katiedotson.weatherapp.databinding.FragmentWeatherBinding

class WeatherFragment : Fragment(R.layout.fragment_weather) {

    // ideally view models would be instantiated entirely by Dagger
    private val viewModel: WeatherViewModel by viewModels { WeatherViewModel.Factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentWeatherBinding.bind(view)

        viewModel.weatherState.observe(viewLifecycleOwner) {
            when (it) {
                is WeatherViewModel.WeatherState.Success -> {
                    binding.weatherCardView.visibility = View.VISIBLE
                    binding.loadingMsg.visibility = View.GONE
                    binding.errorMsg.visibility = View.GONE

                    binding.main.text = it.weather.main
                    binding.description.text = it.weather.description
                    binding.city.text = it.city
                    // TODO: this URL belongs in the build config
                    Glide.with(this)
                        .load("https://openweathermap.org/img/wn/${it.weather.icon}@2x.png")
                        .into(binding.icon)
                }

                WeatherViewModel.WeatherState.Loading -> {
                    binding.weatherCardView.visibility = View.GONE
                    binding.loadingMsg.visibility = View.VISIBLE
                    binding.errorMsg.visibility = View.GONE
                }

                WeatherViewModel.WeatherState.Error -> {
                    binding.weatherCardView.visibility = View.GONE
                    binding.loadingMsg.visibility = View.GONE
                    binding.errorMsg.visibility = View.VISIBLE
                }
            }
        }
    }

}