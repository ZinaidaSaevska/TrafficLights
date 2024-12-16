package com.zinaida.trafficlights

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.zinaida.trafficlights.databinding.FragmentCarModelBinding

class FragmentCarModel : Fragment() {
    private lateinit var binding: FragmentCarModelBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCarModelBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        with(binding) {
            btnStart.setOnClickListener {
                if (etCarModel.text.length < 3) {
                    etCarModel.error = getString(R.string.car_model_length_error)
                } else {
                    findNavController().navigate(
                        FragmentCarModelDirections.actionFragmentCarModelToFragmentTrafficLights(
                            etCarModel.text.toString()
                        )
                    )
                }
            }
        }
    }
}