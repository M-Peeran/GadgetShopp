package com.peeranm.gadgetshopp.feature_gadgets_shopping.presentation.gedgets

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.peeranm.gadgetshopp.R
import com.peeranm.gadgetshopp.core.utils.Resource
import com.peeranm.gadgetshopp.core.utils.collectWithLifecycle
import com.peeranm.gadgetshopp.core.utils.setActionBarTitle
import com.peeranm.gadgetshopp.databinding.GadgetsFragmentBinding
import com.peeranm.gadgetshopp.feature_gadgets_shopping.model.Gadget
import com.peeranm.gadgetshopp.feature_gadgets_shopping.utils.GadgetAdapter
import com.peeranm.gadgetshopp.feature_gadgets_shopping.utils.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GadgetsFragment : Fragment(), OnItemClickListener {

    private val viewModel: GadgetsViewModel by viewModels()

    private var _binding: GadgetsFragmentBinding? = null
    private val binding: GadgetsFragmentBinding
    get() = _binding!!

    private var adapter: GadgetAdapter? = null
    private var cartItemCount = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GadgetsFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setActionBarTitle(R.string.gadgets_feed)

        adapter = GadgetAdapter(requireContext(), lifecycleScope, this)

        binding.apply {
            val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            listGadgets.layoutManager = layoutManager
            listGadgets.addItemDecoration(DividerItemDecoration(requireContext(), layoutManager.orientation))
            listGadgets.adapter = adapter
        }

        collectWithLifecycle(viewModel.cartItemsCount) {
            cartItemCount = it
            requireActivity().invalidateOptionsMenu()
        }

        collectWithLifecycle(viewModel.gadgets) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    showProgressbar()
                }
                is Resource.Success -> {
                    hideProgressbar()
                    adapter?.submitData(resource.data)
                }
                is Resource.Error -> {
                    hideProgressbar()
                    Snackbar.make(view, resource.message, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun <T> onItemClick(view: View?, data: T, position: Int) {
        findNavController().navigate(
            GadgetsFragmentDirections.actionGadgetsFragmentToGadgetDetailsFragment(data as Gadget)
        )
    }

    private fun showProgressbar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressbar() {
        binding.progressBar.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        val menuItem = menu.findItem(R.id.actionCart)
        val actionView = menuItem.actionView
        val cartCounter = actionView.findViewById<TextView>(R.id.textItemCounter)
        cartCounter.text = if (cartItemCount == 0) {
            cartCounter.visibility = View.GONE
            ""
        } else {
            cartCounter.visibility = View.VISIBLE
            cartItemCount.toString()
        }
        actionView.setOnClickListener { onOptionsItemSelected(menuItem) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionCart -> {
                findNavController().navigate(
                    GadgetsFragmentDirections.actionGadgetsFragmentToShoppingCartFragment()
                )
                true
            }
            R.id.actionOrders -> {
                findNavController().navigate(
                    GadgetsFragmentDirections.actionGadgetsFragmentToOrdersFragment()
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter?.onClear()
        _binding = null
        adapter = null
    }

}