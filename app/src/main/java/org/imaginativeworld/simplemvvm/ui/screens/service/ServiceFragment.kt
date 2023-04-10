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

package org.imaginativeworld.simplemvvm.ui.screens.service

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.FragmentServiceBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.interfaces.OnFragmentInteractionListener

@SuppressLint("SetTextI18n")
class ServiceFragment : Fragment(R.layout.fragment_service), CommonFunctions {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var binding: FragmentServiceBinding

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val count = intent?.getIntExtra(CountService.BROADCAST_KEY_COUNT, 0)

            binding.tvCount.text = "$count"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Register Broadcast
        requireContext().registerReceiver(
            receiver,
            IntentFilter(CountService.BROADCAST_SERVICE_INTENT)
        )

        initObservers()
    }

    override fun onDestroy() {
        super.onDestroy()

        // Unregister Broadcast
        requireContext().unregisterReceiver(receiver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentServiceBinding.bind(view)

        initViews()
        initListeners()
    }

    override fun initObservers() {
        /* no-op */
    }

    override fun initViews() {
        updateUI(isServiceEnabled())
    }

    override fun initListeners() {
        binding.btnActionToggle.setOnClickListener {
            val isServiceRunning = isServiceEnabled()
            if (isServiceRunning) {
                updateUI(false)

                Intent(requireContext(), CountService::class.java).also { intent ->
                    intent.putExtra(CountService.BUNDLE_KEY_STOP_SERVICE, true)
                    ContextCompat.startForegroundService(
                        requireActivity(),
                        intent
                    )
                }
            } else {
                updateUI(true)

                Intent(requireContext(), CountService::class.java).also { intent ->
                    requireActivity().startService(intent)
                }
            }
        }
    }

    private fun updateUI(isServiceEnabled: Boolean) {
        if (isServiceEnabled) {
            binding.btnActionToggle.text = "Stop"

            ContextCompat.getDrawable(requireContext(), R.drawable.round_stop_24)
                .also { drawable ->
                    binding.btnActionToggle.icon = drawable
                }
        } else {
            binding.btnActionToggle.text = "Start"

            ContextCompat.getDrawable(requireContext(), R.drawable.round_play_arrow_24)
                .also { drawable ->
                    binding.btnActionToggle.icon = drawable
                }
        }
    }

    @Suppress("DEPRECATION")
    private fun isServiceEnabled(): Boolean {
        val serviceName = CountService::class.java.name

        val manager = requireContext().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val services = manager.getRunningServices(Int.MAX_VALUE)
        for (service in services) {
            if (serviceName == service.service.className) {
                return true
            }
        }

        return false
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
