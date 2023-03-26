package org.imaginativeworld.simplemvvm.ui.screens.demo_activity_nav

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.FragmentDemoActivityNavBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.interfaces.OnFragmentInteractionListener
import org.imaginativeworld.simplemvvm.ui.screens.demo_activity_nav.activities.DemoOneActivity
import org.imaginativeworld.simplemvvm.ui.screens.demo_activity_nav.activities.DemoTwoActivity
import org.imaginativeworld.simplemvvm.ui.screens.demo_activity_nav.activities.DemoTwoActivityData

class DemoActivityNavFragment : Fragment(), CommonFunctions {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var binding: FragmentDemoActivityNavBinding

    private val getActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data?.getParcelableExtra<DemoTwoActivityData>(
                DemoTwoActivity.PARAM_DATA
            )

            if (result.resultCode == Activity.RESULT_OK && data != null) {
                binding.txtResult.text = """
                    Data One: ${data.dataOne}
                    Data Two: ${data.dataTwo}
                """.trimIndent()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDemoActivityNavBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listener?.setAppTitle(getString(R.string.app_name))

        listener?.hideLoading()

        initViews()
    }

    override fun initViews() {
        binding.btnLaunchActivity.setOnClickListener {
            val dataOne: Int = binding.inputDataOne.text.toString().toIntOrNull() ?: -1
            val dataTwo: String = binding.inputDataTwo.text.toString()

            activity?.let {
                DemoOneActivity.start(
                    it,
                    dataOne,
                    dataTwo
                )
            }
        }

        binding.btnLaunchActivityForResult.setOnClickListener {
            activity?.let {
                getActivityResult.launch(Intent(activity, DemoTwoActivity::class.java))
            }
        }
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
