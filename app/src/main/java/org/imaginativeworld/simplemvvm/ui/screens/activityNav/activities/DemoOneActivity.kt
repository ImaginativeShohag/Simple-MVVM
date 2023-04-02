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

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.imaginativeworld.simplemvvm.databinding.ActivityDemoOneBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions

class DemoOneActivity : AppCompatActivity(), CommonFunctions {
    companion object {
        private const val PARAM_DATA_ONE = "data_one"
        private const val PARAM_DATA_TWO = "data_two"

        fun start(activity: Activity, dataOne: Int, dataTwo: String?) {
            val intent = Intent(activity, DemoOneActivity::class.java)
            intent.apply {
                putExtra(PARAM_DATA_ONE, dataOne)
                putExtra(PARAM_DATA_TWO, dataTwo)
            }
            activity.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityDemoOneBinding

    private var dataOne: Int = -1
    private var dataTwo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDemoOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Receive Data
        intent.apply {
            dataOne = getIntExtra(PARAM_DATA_ONE, -1)
            dataTwo = getStringExtra(PARAM_DATA_TWO)
        }

        initViews()
        initListeners()
    }

    override fun initViews() {
        binding.apply {
            txtOne.text = "$dataOne"
            txtTwo.text = "$dataTwo"
        }
    }

    override fun initListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}
