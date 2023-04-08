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

package org.imaginativeworld.simplemvvm.ui.screens.cms

import android.animation.Animator
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.ActivityCmsMainBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.utils.Utils

@AndroidEntryPoint
class CMSMainActivity : AppCompatActivity(), CommonFunctions {

    private lateinit var binding: ActivityCmsMainBinding
    private lateinit var navController: NavController

    private val viewModel: CMSMainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCmsMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        initObservers()
        initViews()
        initListeners()
    }

    override fun initObservers() {
        viewModel.isLoadingVisible.observe(this) { isLoadingVisible ->
            if (isLoadingVisible) {
                Utils.ignoreCrash {
                    binding.loadingView.globalLoadingLayout.animate()
                        .alpha(1f)
                        .setDuration(200)
                        .setListener(object : Animator.AnimatorListener {
                            override fun onAnimationStart(animation: Animator) {
                                binding.loadingView.globalLoadingLayout.alpha = 0f
                                binding.loadingView.globalLoadingLayout.visibility = View.VISIBLE
                            }

                            override fun onAnimationEnd(animation: Animator) {}
                            override fun onAnimationCancel(animation: Animator) {}
                            override fun onAnimationRepeat(animation: Animator) {}
                        })
                        .start()
                }
            } else {
                Utils.ignoreCrash {
                    binding.loadingView.globalLoadingLayout.animate()
                        .alpha(0f)
                        .setDuration(200)
                        .setListener(object : Animator.AnimatorListener {
                            override fun onAnimationEnd(animation: Animator) {
                                binding.loadingView.globalLoadingLayout.visibility = View.GONE
                            }

                            override fun onAnimationRepeat(animation: Animator) {}
                            override fun onAnimationCancel(animation: Animator) {}
                            override fun onAnimationStart(animation: Animator) {}
                        })
                        .start()
                }
            }
        }
    }

    override fun initViews() {
        /* no-op */
    }

    override fun initListeners() {
        /* no-op */
    }
}
