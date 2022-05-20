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
import com.peeranm.gadgetshopp.databinding.ShoppingCartItemBinding
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.entities.ShoppingCartItem
import kotlinx.coroutines.launch

class ShoppingCartAdapter(
    context: Context,
    scope: LifecycleCoroutineScope,
    listener: OnItemClickListener
): RecyclerView.Adapter<ShoppingCartAdapter.CartItemHolder>() {

    private var context: Context? = null
    private var scope: LifecycleCoroutineScope? = null
    private var listener: OnItemClickListener? = null

    init {
        this.context = context
        this.scope = scope
        this.listener = listener
    }

    inner class CartItemHolder(private val binding: ShoppingCartItemBinding)
        : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.apply {
                btnIncrementQuantity.setOnClickListener(this@CartItemHolder)
                btnDecrementQuanity.setOnClickListener(this@CartItemHolder)
                btnRemoveFromCart.setOnClickListener(this@CartItemHolder)
            }
        }

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

        fun bind(item: ShoppingCartItem) {
            binding.apply {
                textName.text = item.name
                textPrice.text = String.format(context?.getString(R.string.rs_template)!!, item.price.toString())
                textQuantity.text = item.quantity.toString()
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

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<ShoppingCartItem>() {
        override fun areItemsTheSame(oldItem: ShoppingCartItem, newItem: ShoppingCartItem): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: ShoppingCartItem, newItem: ShoppingCartItem): Boolean {
            return oldItem == newItem
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemHolder {
        return CartItemHolder(
            ShoppingCartItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartItemHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    fun submitData(data: List<ShoppingCartItem>) {
        differ.submitList(data)
    }

    fun onClear() {
        this.scope = null
        this.context = null
        this.listener = null
    }
}