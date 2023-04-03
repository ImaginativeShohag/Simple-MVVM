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

package org.imaginativeworld.simplemvvm.ui.screens.activityNav.activities

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import kotlinx.parcelize.Parcelize
import org.imaginativeworld.simplemvvm.databinding.ActivityDemoTwoBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions

class DemoTwoActivity : AppCompatActivity(), CommonFunctions {

    companion object {
        const val PARAM_DATA = "data"
    }

    private lateinit var binding: ActivityDemoTwoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDemoTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
    }

    override fun initListeners() {
        binding.btnFinishActivity.setOnClickListener {
            val data = DemoTwoActivityData(
                dataOne = binding.inputDataOne.text.toString().toIntOrNull() ?: -1,
                dataTwo = binding.inputDataTwo.text.toString()
            )
            val intent = Intent()
                .apply {
                    putExtra(PARAM_DATA, data)
                }
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}

@Parcelize
data class DemoTwoActivityData(
    val dataOne: Int,
    val dataTwo: String?
) : Parcelable
