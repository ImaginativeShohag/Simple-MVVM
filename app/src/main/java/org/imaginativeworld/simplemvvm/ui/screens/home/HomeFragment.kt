/*
 * Copyright 2023 Md. Mahmudul Hasan Shohag
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ------------------------------------------------------------------------
 *
 * Project: Simple MVVM
 * Developed by: @ImaginativeShohag
 *
 * Md. Mahmudul Hasan Shohag
 * imaginativeshohag@gmail.com
 *
 * Source: https://github.com/ImaginativeShohag/Simple-MVVM
 */

package org.imaginativeworld.simplemvvm.ui.screens.home

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
class HomeFragment : Fragment(R.layout.demo_fragment_home), CommonFunctions {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var binding: DemoFragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()

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
    ): View? {
        Timber.d("onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.d("onViewCreated")
        super.onViewCreated(view, savedInstanceState)

        binding = DemoFragmentHomeBinding.bind(view)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel

        listener?.setAppTitle(getString(R.string.app_name))

        listener?.hideLoading()

        initViews()

        initListeners()
    }

    override fun initViews() {
        NoInternetSnackbarFire.Builder(binding.mainContainer, viewLifecycleOwner.lifecycle).build()
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

        binding.btnCmsApp.setOnClickListener {
            listener?.navigate(R.id.cmsApp)
        }

        binding.btnService.setOnClickListener {
            listener?.navigate(R.id.serviceFragment)
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
