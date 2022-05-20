package com.peeranm.gadgetshopp.feature_gadgets_shopping.presentation.order_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.imageLoader
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.peeranm.gadgetshopp.R
import com.peeranm.gadgetshopp.core.utils.collectWithLifecycle
import com.peeranm.gadgetshopp.core.utils.getDateAndTimeFromTimestamp
import com.peeranm.gadgetshopp.core.utils.getOrderState
import com.peeranm.gadgetshopp.databinding.OrderDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderDetailsFragment : Fragment() {

    private var _binding: OrderDetailsFragmentBinding? = null
    private val binding: OrderDetailsFragmentBinding
    get() = _binding!!

    private val viewModel: OrderDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = OrderDetailsFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectWithLifecycle(viewModel.selectedOrder) { item ->
            binding.apply {
                textName.text = item.name
                textOrderStatus.text = getString(getOrderState(item.orderStatus))
                textPrice.text = String.format(getString(R.string.rs_template), item.price.toString())
                textTimestamp.text = getString(
                    R.string.purchase_timestamp,
                    getDateAndTimeFromTimestamp(item.timestamp)
                )
                loadImage(item.imageUrl)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}