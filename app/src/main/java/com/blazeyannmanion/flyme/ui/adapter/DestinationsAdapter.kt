package com.blazeyannmanion.flyme.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blazeyannmanion.flyme.databinding.DestinationItemBinding
import com.blazeyannmanion.flyme.model.Destination

class DestinationsAdapter(
    private var onClickListener: OnDestinationClickListener
) : RecyclerView.Adapter<DestinationsAdapter.DestinationViewHolder>() {

    private var items = ArrayList<Destination>()

    fun submitList(data: ArrayList<Destination>) {
        this.items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationViewHolder {
        val inflater = DestinationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DestinationViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: DestinationViewHolder, position: Int) {
        holder.bind(items[position], onClickListener)
    }

    override fun getItemCount() = items.size

    fun clearData() {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }

    class DestinationViewHolder(private val binding: DestinationItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val txtDestination = binding.txtDestination

        fun bind(item: Destination, action: OnDestinationClickListener) {
            txtDestination.text = item.city
            binding.root.setOnClickListener {
                action.onDestinationClicked(item, adapterPosition)
            }
        }
    }

    interface OnDestinationClickListener {
        // create interface to grab item at specific position
        fun onDestinationClicked(item: Destination, position: Int) {}
    }
}