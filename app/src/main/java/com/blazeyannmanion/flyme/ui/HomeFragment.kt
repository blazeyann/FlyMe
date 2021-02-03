package com.blazeyannmanion.flyme.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.blazeyannmanion.flyme.R
import com.blazeyannmanion.flyme.databinding.HomeFragmentBinding
import com.blazeyannmanion.flyme.model.Destination
import com.blazeyannmanion.flyme.ui.adapter.DestinationsAdapter
import com.blazeyannmanion.flyme.viewmodel.DestinationViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "Home"

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment), DestinationsAdapter.OnDestinationClickListener {

    @Inject
    lateinit var viewModel: DestinationViewModel

    private lateinit var binding: HomeFragmentBinding

    private lateinit var adapter: DestinationsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = HomeFragmentBinding.bind(view)

        setupToolBar()
        setupBtnGetDestinations()
        setupFab()
        setupRecyclerView()
        observe()
    }

    private fun setupToolBar() {
        binding.toolbar.title = TAG
    }

    private fun setupBtnGetDestinations() {
        binding.button.setOnClickListener {
            adapter.clearData()
            binding.progressBar.visibility = View.VISIBLE
            viewModel.getDestinations()
        }
    }

    private fun setupFab() {
        binding.fab.setOnClickListener {
            val action = HomeFragmentDirections.openAddDestinationFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() {
        adapter = DestinationsAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter

        val decoration = DividerItemDecoration(activity, VERTICAL)
        binding.recyclerView.addItemDecoration(decoration)
    }

    private fun observe() {
        // observe destinations list returned by server
        viewModel.observeDestinations().observe(viewLifecycleOwner, { destinationList ->
            if (destinationList != null) {
                binding.progressBar.visibility = View.GONE
                binding.txtHeader.visibility = View.VISIBLE

                adapter.submitList(destinationList)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show()
            }
        })
        setFragmentResultListener(AddDestinationFragment.TAG) { requestKey, bundle ->
            // reload destination list when user has finished adding new destination
            if (AddDestinationFragment.TAG == requestKey ) {
                val loadDestinations = (bundle.getSerializable(AddDestinationFragment.TAG) as AddDestinationFragmentResponse).loadDestinations
                if (loadDestinations)
                    viewModel.getDestinations()
            }
        }
    }

    override fun onDestinationClicked(item: Destination, position: Int) {
        // open destination details bottom sheet
        val action = HomeFragmentDirections.openDestinationDetailsBottomSheet(item.city, item.country, item.description)
        findNavController().navigate(action)
    }
}