package com.blazeyannmanion.flyme.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.blazeyannmanion.flyme.R
import com.blazeyannmanion.flyme.databinding.AddDestinationFragmentBinding
import com.blazeyannmanion.flyme.model.Destination
import com.blazeyannmanion.flyme.viewmodel.DestinationViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddDestinationFragment : Fragment(R.layout.add_destination_fragment) {

    private var _binding: AddDestinationFragmentBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: DestinationViewModel

    companion object {
        const val TAG = "Add Destination"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddDestinationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar()
        setupBtnAddDestination()
        setUpTextChangeListeners()
        observe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            title = TAG
            navigationIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_back, null)
            setNavigationOnClickListener {
                hideKeyboard()
                activity?.onBackPressed()
            }
        }
    }

    private fun observe() {
        // observe server response after adding a new destination
        viewModel.observeOnNewDestinationCreated().observe(viewLifecycleOwner, { newDestination ->
            if (newDestination != null) {
                // let user know new destination was successfully added
                Toast.makeText(activity, getString(R.string.new_destination_added), Toast.LENGTH_LONG).show()
                hideKeyboard()

                // notify home fragment to refresh destination list
                setFragmentResult(TAG, bundleOf(AddDestinationFragmentResponse.KEY to AddDestinationFragmentResponse(loadDestinations = true)))

                activity?.onBackPressed()
            } else {
                // let user know there was an error adding new destination
                Toast.makeText(activity, getString(R.string.error_adding_destination), Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setupBtnAddDestination() {
        binding.button.setOnClickListener {

            // check that city editText contains text
            if (binding.txtCity.text.isNullOrBlank()) {
                binding.cityInputLayout.isErrorEnabled = true
                binding.cityInputLayout.error = getString(R.string.please_enter_city)
                return@setOnClickListener
            }

            // check that country editText contains text
            if (binding.txtCountry.text.isNullOrBlank()) {
                binding.countryInputLayout.isErrorEnabled = true
                binding.countryInputLayout.error = getString(R.string.please_enter_country)
                return@setOnClickListener
            }

            // check that description editText contains text
            if (binding.txtDescription.text.isNullOrBlank()) {
                binding.descriptionInputLayout.isErrorEnabled = true
                binding.descriptionInputLayout.error = getString(R.string.please_enter_description)
                return@setOnClickListener
            }

            val city = binding.txtCity.text.toString()
            val description = binding.txtDescription.text.toString()
            val country = binding.txtCountry.text.toString()
            val newDestination = Destination(city, description, country)

            // send the new destination to the viewModel
            viewModel.addNewDestination(newDestination)
        }
    }

    private fun setUpTextChangeListeners() {
        binding.txtCity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null)
                // clear the city inputLayout error message when text is entered
                    binding.cityInputLayout.isErrorEnabled = false
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.txtDescription.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null)
                // clear the description inputLayout error message when text is entered
                    binding.descriptionInputLayout.isErrorEnabled = false
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.txtCountry.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null)
                // clear the country inputLayout error message when text is entered
                    binding.countryInputLayout.isErrorEnabled = false
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}