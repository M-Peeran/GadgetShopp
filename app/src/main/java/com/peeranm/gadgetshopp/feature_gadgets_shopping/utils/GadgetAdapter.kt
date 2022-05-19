package com.peeranm.gadgetshopp.feature_gadgets_shopping.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.peeranm.gadgetshopp.R
import com.peeranm.gadgetshopp.databinding.GadgetItemBinding
import com.peeranm.gadgetshopp.feature_gadgets_shopping.model.Gadget
import kotlinx.coroutines.launch

class GadgetAdapter(
    context: Context,
    scope: LifecycleCoroutineScope,
    listener: OnItemClickListener
) : RecyclerView.Adapter<GadgetAdapter.GadgetHolder>() {

    private var context: Context? = null
    private var scope: LifecycleCoroutineScope? = null
    private var listener: OnItemClickListener? = null

    init {
        this.context = context
        this.scope = scope
        this.listener = listener
    }

    inner class GadgetHolder(private val binding: GadgetItemBinding)
        : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init { binding.root.setOnClickListener(this) }

        private fun loadImage(imageUrl: String) {
            context?.let {
                val imageRequest = ImageRequest.Builder(it)
                    .data(imageUrl)
                    .transformations(RoundedCornersTransformation(radius = 8f))
                    .placeholder(R.drawable.ic_baseline_image)
                    .target(binding.imageGadgetImage)
                    .build()
                scope?.launch { it.imageLoader.execute(imageRequest) }
            }
        }

        fun bind(gadget: Gadget) {
            binding.apply {
                textName.text = gadget.name
                textPrice.text = String.format(context?.getString(R.string.rs_template)!!, gadget.price.toString())
                textRating.text = gadget.rating.toString()
                loadImage(gadget.imageUrl)
            }
        }

        override fun onClick(view: View?) {
            listener?.onItemClick(
                view = view,
                data = differ.currentList[adapterPosition],
                position = adapterPosition
            )
        }
    }

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Gadget>() {
        override fun areItemsTheSame(oldItem: Gadget, newItem: Gadget): Boolean {
            return oldItem.name == newItem.name
        }
        override fun areContentsTheSame(oldItem: Gadget, newItem: Gadget): Boolean {
            return oldItem == newItem
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GadgetHolder {
        return GadgetHolder(
            GadgetItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GadgetHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    fun submitData(data: List<Gadget>) {
        differ.submitList(data)
    }

    fun onClear() {
        this.scope = null
        this.context = null
        this.listener = null
    }
}