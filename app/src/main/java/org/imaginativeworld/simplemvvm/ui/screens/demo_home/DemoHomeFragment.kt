package org.imaginativeworld.simplemvvm.ui.screens.demo_home

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.oopsnointernet.snackbars.fire.NoInternetSnackbarFire
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.DemoFragmentHomeBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.interfaces.OnFragmentInteractionListener
import org.imaginativeworld.simplemvvm.ui.components.customsnackbar.CustomSnackbar
import timber.log.Timber

@AndroidEntryPoint
class DemoHomeFragment : Fragment(), CommonFunctions {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var binding: DemoFragmentHomeBinding

    private val viewModel: DemoHomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate")
        super.onCreate(savedInstanceState)

        initObservers()

//        NoInternetDialogSignal.Builder(requireActivity(), lifecycle).build()
//        NoInternetDialogPendulum.Builder(requireActivity(), lifecycle).build()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.d("onCreateView")

        binding = DemoFragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel

        NoInternetSnackbarFire.Builder(binding.mainContainer, viewLifecycleOwner.lifecycle).build()

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
            listener?.navigate(R.id.userFragment)
        }

        binding.btnPost.setOnClickListener {
            listener?.navigate(R.id.postFragment)
        }

        binding.btnPostPaged.setOnClickListener {
            listener?.navigate(R.id.postPagedFragment)
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

        binding.btnCarousel.setOnClickListener {
            listener?.navigate(R.id.demoCarouselFragment)
        }

        binding.btnDarkMode.setOnClickListener {
            val isNightTheme = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

            when (isNightTheme) {
                Configuration.UI_MODE_NIGHT_YES ->
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Configuration.UI_MODE_NIGHT_NO ->
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

        binding.btnLatex.setOnClickListener {
            listener?.navigate(R.id.demoLatexFragment)
        }

        binding.btnActivityNav.setOnClickListener {
            listener?.navigate(R.id.demoActivityNavFragment)
        }

        binding.btnFragmentNav.setOnClickListener {
            listener?.navigate(R.id.demoFragmentNavFragment)
        }

        binding.btnTodosApp.setOnClickListener {
            listener?.navigate(R.id.awesomeTodosMainActivity)
        }
    }

    override fun onAttach(context: Context) {
        Timber.d("onAttach")
        super.onAttach(context)

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
