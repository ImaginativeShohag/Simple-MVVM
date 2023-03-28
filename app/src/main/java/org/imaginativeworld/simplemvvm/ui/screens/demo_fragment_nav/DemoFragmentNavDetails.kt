package org.imaginativeworld.simplemvvm.ui.screens.demo_fragment_nav

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.FragmentDemoFragmentNavDetailsBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.ui.screens.demo_fragment_nav.viewmodel.DemoFragmentNavViewModel

class DemoFragmentNavDetails :
    Fragment(R.layout.fragment_demo_fragment_nav_details),
    CommonFunctions {

    private val parentViewModel: DemoFragmentNavViewModel by viewModels(ownerProducer = {
        requireParentFragment()
    })

    private lateinit var binding: FragmentDemoFragmentNavDetailsBinding

    private var emptyText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.apply {
            emptyText = getString(ARG_EMPTY_TEXT, "")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentDemoFragmentNavDetailsBinding.bind(view)

        initObservers()

        initViews()

        initListeners()
    }

    override fun initObservers() {
        // Pass data between fragments: using parent `ViewModel`.
        parentViewModel.selectedItem.observe(this.viewLifecycleOwner) { text ->
            binding.textView.text = text
        }
    }

    override fun initViews() {
        binding.textView.text = emptyText
    }

    override fun initListeners() {
        binding.btnShuffle.setOnClickListener {
            setFragmentResult(
                DemoFragmentNavList.REQUEST_KEY,
                bundleOf(
                    DemoFragmentNavList.PARAM_ACTION to DemoFragmentNavList.PARAM_ACTION_SHUFFLE
                )
            )
        }
    }

    companion object {
        const val ARG_EMPTY_TEXT = "empty_text"
    }
}
