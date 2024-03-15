package xyz.katiedotson.weatherapp.home

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import xyz.katiedotson.weatherapp.R
import xyz.katiedotson.weatherapp.appComponent
import xyz.katiedotson.weatherapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home), HasDefaultViewModelProviderFactory {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { wasGranted ->
            viewModel.handlePermissionResult(wasGranted)
        }

    // ideally view models would be initialized entirely by Dagger
    private val viewModel: HomeViewModel by viewModels { HomeViewModel.Factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)

        binding.enableLocationBtn.setOnClickListener {
            requestLocationPermission()
        }

        binding.skipPermissionsBtn.setOnClickListener {
            viewModel.handlePermissionResult(wasGranted = false)
        }

        // used live data in the other view model
        lifecycleScope.launch {
            viewModel.events.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                it.lastOrNull()?.let { event ->
                    viewModel.markEventHandled(event)
                    when (event) {
                        HomeViewModel.Event.LocationPermissionAllowed -> {
                            findNavController().navigate(R.id.action_homeFragment_to_weatherFragment_pop)
                        }

                        HomeViewModel.Event.LocationPermissionDenied -> {
                            findNavController().navigate(R.id.action_homeFragment_to_weatherFragment)
                        }
                    }
                }
            }
        }
    }

    private fun requestLocationPermission() {
        when {
            ActivityCompat.shouldShowRequestPermissionRationale(
                this.requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) -> {
                // TODO: this should show a rationale
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }

            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        }
    }

}