package com.blazeyannmanion.flyme.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.navArgs
import com.blazeyannmanion.flyme.R
import com.blazeyannmanion.flyme.databinding.DestinationDetailsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DestinationDetailsBottomSheet : BottomSheetDialogFragment() {

    private var _binding: DestinationDetailsBottomSheetBinding? = null

    private val binding get() = _binding!!

    val args: DestinationDetailsBottomSheetArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?) =
        BottomSheetDialog(requireContext(), theme)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DestinationDetailsBottomSheetBinding.inflate(inflater, container, false)
        val view = binding.root

        view.findViewById<ConstraintLayout>(R.id.root_layout_of_bottom_sheet)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
    }

    private fun setupViews() {
        val city = args.city
        val country = args.country
        val description = args.description

        binding.txtCity.text = getString(R.string.city_placeholder, city)
        binding.txtCountry.text = getString(R.string.country_placeholder, country)
        binding.txtDescription.text = getString(R.string.description_placeholder, description)
    }
}