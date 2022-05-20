package com.peeranm.gadgetshopp.feature_gadgets_shopping.presentation.orders

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
import com.peeranm.gadgetshopp.core.utils.collectWithLifecycle
import com.peeranm.gadgetshopp.core.utils.setActionBarTitle
import com.peeranm.gadgetshopp.databinding.OrdersFragmentBinding
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.entities.OrderItem
import com.peeranm.gadgetshopp.feature_gadgets_shopping.utils.OnItemClickListener
import com.peeranm.gadgetshopp.feature_gadgets_shopping.utils.OrderAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : Fragment(), OnItemClickListener {

    private val viewModel: OrdersViewModel by viewModels()

    private var _binding: OrdersFragmentBinding? = null
    private val binding: OrdersFragmentBinding
    get() = _binding!!

    private var adapter: OrderAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = OrdersFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBarTitle(R.string.your_orders)

        adapter = OrderAdapter(requireContext(), lifecycleScope, this)
        binding.apply {
            val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            listOrders.layoutManager = layoutManager
            listOrders.addItemDecoration(DividerItemDecoration(requireContext(), layoutManager.orientation))
            listOrders.adapter = adapter
        }

        collectWithLifecycle(viewModel.orders) {
            adapter?.submitData(it)
        }

    }

    override fun <T> onItemClick(view: View?, data: T, position: Int) {
        data as OrderItem
        findNavController().navigate(
            OrdersFragmentDirections.actionOrdersFragmentToOrderDetailsFragment(data)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter?.onClear()
        adapter = null
        _binding = null
    }

}