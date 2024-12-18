package com.zinaida.trafficlights

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.zinaida.trafficlights.databinding.FragmentTrafficLightsBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val ORANGE_LIGHT_DURATION = 1000L
private const val LIGHT_DURATION = 4000L

class FragmentTrafficLights : Fragment() {
    private lateinit var binding: FragmentTrafficLightsBinding
    private val arguments : FragmentTrafficLightsArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrafficLightsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvCarModel.text = arguments.carModel
        setUpTrafficLights()
    }

    private fun setUpTrafficLights() {
        lifecycleScope.launch {
            while (true) {
                //Red
                setUpLight(
                    LIGHT_DURATION,
                    R.color.red_active,
                    R.color.red_inactive,
                    binding.ivRedLight
                )

                //Orange
                setUpLight(
                    ORANGE_LIGHT_DURATION,
                    R.color.orange_active,
                    R.color.orange_inactive,
                    binding.ivOrangeLight
                )

                //Green
                setUpLight(
                    LIGHT_DURATION,
                    R.color.green_active,
                    R.color.green_inactive,
                    binding.ivGreenLight
                )
            }
        }
    }

    private suspend fun setUpLight(duration: Long, colorResFrom: Int, colorResTo: Int, view: ImageView) {
        view.setColorFilter(ContextCompat.getColor(requireContext(), colorResFrom))

        delay(duration)

        view.setColorFilter(ContextCompat.getColor(requireContext(), colorResTo))
    }
}