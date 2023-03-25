package org.imaginativeworld.simplemvvm.ui.screens.demo_activity_nav.activities

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
