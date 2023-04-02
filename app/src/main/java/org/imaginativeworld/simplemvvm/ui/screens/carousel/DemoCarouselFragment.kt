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

package org.imaginativeworld.simplemvvm.ui.screens.carousel

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.DemoFragmentCarouselBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.interfaces.OnFragmentInteractionListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import kotlin.random.Random

class DemoCarouselFragment : Fragment(), CommonFunctions {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var binding: DemoFragmentCarouselBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DemoFragmentCarouselBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listener?.setAppTitle(getString(R.string.app_name))

        listener?.hideLoading()

        initViews()

        initListeners()
    }

    override fun initViews() {
        val list = mutableListOf<CarouselItem>()

        list.add(
            CarouselItem(
                imageUrl = "https://picsum.photos/1080?${Random.nextInt()}",
                caption = "Awesome first photo",
            ),
        )

        list.add(
            CarouselItem(
                imageUrl = "https://picsum.photos/1080?${Random.nextInt()}",
                caption = "Beautiful second photo",
            ),
        )

        list.add(
            CarouselItem(
                imageUrl = "https://picsum.photos/1080?${Random.nextInt()}",
                caption = "Cool third photo",
            ),
        )

        binding.carousel.setData(list)
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
