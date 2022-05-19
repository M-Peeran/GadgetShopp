package com.peeranm.gadgetshopp.feature_gadgets_shopping.presentation.gadget_details

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.imageLoader
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.peeranm.gadgetshopp.R
import com.peeranm.gadgetshopp.core.utils.Constants
import com.peeranm.gadgetshopp.core.utils.collectLatestWithLifecycle
import com.peeranm.gadgetshopp.core.utils.collectWithLifecycle
import com.peeranm.gadgetshopp.core.utils.setActionBarTitle
import com.peeranm.gadgetshopp.databinding.GadgetDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GadgetDetailsFragment : Fragment() {

    private val viewModel: GadgetDetailsViewModel by viewModels()

    private var _binding: GadgetDetailsFragmentBinding? = null
    private val binding: GadgetDetailsFragmentBinding
    get() = _binding!!

    private var cartItemCount = 0
    private var animateNow = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GadgetDetailsFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setActionBarTitle(R.string.gadget_details)

        binding.btnAddToCart.setOnClickListener {
            viewModel.onEvent(GadgetDetailsEvent.InsertToCart(viewModel.gadget.value))
            animateNow = true
        }

        collectWithLifecycle(viewModel.cartItemsCount) {
            cartItemCount = it
            requireActivity().invalidateOptionsMenu()
        }

        collectLatestWithLifecycle(viewModel.gadget) {
            binding.apply {
                textName.text = it.name
                textPrice.text = String.format(getString(R.string.rs_template), it.price.toString())
                textRating.text = it.rating.toString()
                loadImage(it.imageUrl)
            }
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
            lifecycleScope.launch { it.imageLoader.execute(imageRequest) }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        val menuItem = menu.findItem(R.id.actionCart)
        val actionView = menuItem.actionView
        val cartCounter = actionView.findViewById<TextView>(R.id.textItemCounter)
        if (cartItemCount != 0) {
            cartCounter.visibility = View.VISIBLE
            cartCounter.text = cartItemCount.toString()
            animateCartIcon(actionView)
        } else cartCounter.visibility = View.GONE
        actionView.setOnClickListener { onOptionsItemSelected(menuItem) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionCart -> {
                findNavController().navigate(
                    GadgetDetailsFragmentDirections.actionGadgetDetailsFragmentToShoppingCartFragment()
                )
                true
            }
            R.id.actionOrders -> {
                findNavController().navigate(
                    GadgetDetailsFragmentDirections.actionGadgetDetailsFragmentToOrdersFragment()
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun animateCartIcon(actionView: View?) {
        if (animateNow) {
            val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                actionView,
                PropertyValuesHolder.ofFloat("scaleX", 0.5f),
                PropertyValuesHolder.ofFloat("scaleY", 0.5f)
            )
            scaleDown.duration = Constants.ANIM_DURATION
            scaleDown.repeatMode = ValueAnimator.REVERSE
            scaleDown.repeatCount = 1
            scaleDown.start()
            animateNow = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}