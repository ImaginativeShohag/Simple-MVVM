package org.imaginativeworld.simplemvvm.views.fragments.demo_home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import org.imaginativeworld.simplemvvm.MyApplication
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.DemoFragmentHomeBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.interfaces.OnFragmentInteractionListener
import org.imaginativeworld.simplemvvm.views.customsnackbar.CustomSnackbar
import timber.log.Timber
import javax.inject.Inject

class DemoHomeFragment : Fragment(), CommonFunctions {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var binding: DemoFragmentHomeBinding

    @Inject
    lateinit var viewModel: DemoHomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate")
        super.onCreate(savedInstanceState)

        initObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("onCreateView")

        binding = DemoFragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.d("onViewCreated")
        super.onViewCreated(view, savedInstanceState)

        listener?.setAppTitle(getString(R.string.app_name))

        listener?.hideLoading()

        initViews()

        initListeners()
    }

    override fun onResume() {
        Timber.d("onResume")
        super.onResume()
    }

    override fun initListeners() {

        binding.btnUser.setOnClickListener {

            listener?.gotoFragment(R.id.userFragment)

        }

        binding.btnPost.setOnClickListener {

            listener?.gotoFragment(R.id.postFragment)

        }

        binding.btnPostPaged.setOnClickListener {

            listener?.gotoFragment(R.id.postPagedFragment)

        }

        binding.btnCustomSnackbar.setOnClickListener {

            CustomSnackbar.make(
                binding.root,
                "Hi! I am a custom Snackbar!",
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction("Ok", View.OnClickListener {})
                .show()

        }

    }

    override fun onAttach(context: Context) {
        Timber.d("onAttach")
        super.onAttach(context)

        (context.applicationContext as MyApplication).appGraph.inject(this)

        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        Timber.d("onDetach")
        super.onDetach()
        listener = null
    }

}
