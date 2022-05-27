package com.peeranm.gadgetshopp.feature_gadgets_shopping.presentation.shopping_cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.peeranm.gadgetshopp.R
import com.peeranm.gadgetshopp.core.utils.Constants
import com.peeranm.gadgetshopp.core.utils.collectWithLifecycle
import com.peeranm.gadgetshopp.core.utils.setActionBarTitle
import com.peeranm.gadgetshopp.databinding.ShoppingCartFragmentBinding
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.entities.ShoppingCartItem
import com.peeranm.gadgetshopp.feature_gadgets_shopping.utils.OnItemClickListener
import com.peeranm.gadgetshopp.feature_gadgets_shopping.utils.ShoppingCartAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShoppingCartFragment : Fragment(), OnItemClickListener<ShoppingCartItem> {

    private val viewModel: ShoppingCartViewModel by viewModels()

    private var _binding: ShoppingCartFragmentBinding? = null
    private val binding: ShoppingCartFragmentBinding
    get() = _binding!!

    private var adapter: ShoppingCartAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ShoppingCartFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBarTitle(R.string.your_cart)

        adapter = ShoppingCartAdapter(requireContext(), lifecycleScope, this)

        collectWithLifecycle(viewModel.shoppingCartItems) { cartItems ->
            adapter?.submitData(cartItems)
        }

        collectWithLifecycle(viewModel.totalPrice) { totalPrice ->
            binding.apply {
                if (totalPrice == 0.0) {
                    if (progressBar.visibility != View.VISIBLE) {
                        textCartMessage.visibility = View.VISIBLE
                    }
                    btnCheckout.visibility = View.GONE
                    textTotalPrice.visibility = View.GONE
                } else {
                    textCartMessage.visibility = View.GONE
                    textTotalPrice.text = String.format(getString(R.string.rs_template), totalPrice.toString())
                    textTotalPrice.visibility = View.VISIBLE
                    btnCheckout.visibility = View.VISIBLE
                }
            }

        }

        binding.apply {
            val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            listShoppingCart.layoutManager = layoutManager
            listShoppingCart.addItemDecoration(DividerItemDecoration(requireContext(), layoutManager.orientation))
            listShoppingCart.adapter = adapter

            btnCheckout.setOnClickListener {
                viewModel.onEvent(ShoppingCartEvent.InsertOrders(viewModel.shoppingCartItems.value))
                lifecycleScope.launch {
                    showProgressbar()
                    delay(Constants.CHECKOUT_DELAY)
                    hideProgressbar()
                    findNavController().navigate(
                        ShoppingCartFragmentDirections.actionShoppingCartFragmentToOrderSuccessfulFragment()
                    )
                }
            }
        }
    }

    override fun onItemClick(view: View?, data: ShoppingCartItem, position: Int) {
        when (view?.id) {
            R.id.btnIncrementQuantity -> viewModel.onEvent(ShoppingCartEvent.IncrementQuantityForItem(data))
            R.id.btnDecrementQuanity -> viewModel.onEvent(ShoppingCartEvent.DecrementQuantityForItem(data))
            R.id.btnRemoveFromCart -> viewModel.onEvent(ShoppingCartEvent.RemoveFromCart(data.id))
        }
    }

    private fun showProgressbar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressbar() {
        binding.progressBar.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter?.onClear()
        adapter = null
        _binding = null
    }
}