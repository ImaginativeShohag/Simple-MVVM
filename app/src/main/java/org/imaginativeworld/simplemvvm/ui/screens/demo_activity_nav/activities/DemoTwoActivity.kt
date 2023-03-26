package org.imaginativeworld.simplemvvm.ui.screens.demo_activity_nav.activities

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
