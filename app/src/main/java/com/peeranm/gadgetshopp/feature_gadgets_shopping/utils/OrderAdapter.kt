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
import com.peeranm.gadgetshopp.core.utils.getDateAndTimeFromTimestamp
import com.peeranm.gadgetshopp.databinding.OrderItemBinding
import com.peeranm.gadgetshopp.databinding.ShoppingCartItemBinding
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.entities.OrderItem
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.entities.ShoppingCartItem
import kotlinx.coroutines.launch

class OrderAdapter(
    context: Context,
    scope: LifecycleCoroutineScope,
    listener: OnItemClickListener<OrderItem>
): RecyclerView.Adapter<OrderAdapter.OrderItemHolder>() {

    private var context: Context? = null
    private var scope: LifecycleCoroutineScope? = null
    private var listener: OnItemClickListener<OrderItem>? = null

    init {
        this.context = context
        this.scope = scope
        this.listener = listener
    }

    inner class OrderItemHolder(private val binding: OrderItemBinding)
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

        fun bind(item: OrderItem) {
            binding.apply {
                textName.text = item.name
                textPrice.text = String.format(context?.getString(R.string.rs_template)!!, item.price.toString())
                textTimestamp.text = context?.getString(
                    R.string.purchase_timestamp,
                    getDateAndTimeFromTimestamp(item.timestamp)
                )
                loadImage(item.imageUrl)
            }
        }

        override fun onClick(view: View?) {
            listener?.onItemClick(
                view = view,
                data = differ.currentList[adapterPosition],
                adapterPosition
            )
        }
    }

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<OrderItem>() {
        override fun areItemsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
            return oldItem == newItem
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemHolder {
        return OrderItemHolder(
            OrderItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OrderItemHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    fun submitData(data: List<OrderItem>) {
        differ.submitList(data)
    }

    fun onClear() {
        this.scope = null
        this.context = null
        this.listener = null
    }
}