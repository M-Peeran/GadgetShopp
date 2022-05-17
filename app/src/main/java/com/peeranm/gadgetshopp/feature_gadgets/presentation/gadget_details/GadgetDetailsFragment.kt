package com.peeranm.gadgetshopp.feature_gadgets.presentation.gadget_details

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
import com.peeranm.gadgetshopp.core.utils.collectLatestWithLifecycle
import com.peeranm.gadgetshopp.core.utils.setActionBarTitle
import com.peeranm.gadgetshopp.databinding.GadgetDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_android_components_ViewComponent
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GadgetDetailsFragment : Fragment() {

    private val viewModel: GadgetDetailsViewModel by viewModels()

    private var _binding: GadgetDetailsFragmentBinding? = null
    private val binding: GadgetDetailsFragmentBinding
    get() = _binding!!

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
        setActionBarTitle(R.string.gadget_details)

        binding.btnAddToCart.setOnClickListener {

        }

        collectLatestWithLifecycle(viewModel.gadget) {
            binding.apply {
                textName.text = it.name
                textPrice.text = it.price.toString()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}