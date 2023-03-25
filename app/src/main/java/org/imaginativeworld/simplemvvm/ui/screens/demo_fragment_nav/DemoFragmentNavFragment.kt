package org.imaginativeworld.simplemvvm.ui.screens.demo_fragment_nav

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.FragmentDemoFragmentNavBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.interfaces.OnFragmentInteractionListener
import org.imaginativeworld.simplemvvm.ui.screens.demo_fragment_nav.viewmodel.DemoFragmentNavViewModel
import timber.log.Timber

class DemoFragmentNavFragment : Fragment(), CommonFunctions {

    private val viewModel: DemoFragmentNavViewModel by viewModels()

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var binding: FragmentDemoFragmentNavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDemoFragmentNavBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listener?.setAppTitle(getString(R.string.app_name))

        listener?.hideLoading()

        // Init fragment programmatically
        if (savedInstanceState == null) {
            val bundle = bundleOf(DemoFragmentNavDetails.PARAM_EMPTY_TEXT to "Nothing selected!")

            childFragmentManager.commit {
                setReorderingAllowed(true)
                add(binding.fragmentContainerDetails.id, DemoFragmentNavDetails::class.java, bundle)
            }
        }

        initViews()
    }

    override fun initObservers() {
        viewModel.selectedItem.observe(this) {
            Timber.d("Selected item: $it")
        }
    }

    override fun initViews() {
        /* no-op */
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
