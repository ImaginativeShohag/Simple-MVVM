package org.imaginativeworld.simplemvvm.views.fragments.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.imaginativeworld.simplemvvm.MyApplication
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.FragmentHomeBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.interfaces.OnFragmentInteractionListener
import javax.inject.Inject

class HomeFragment : Fragment(), CommonFunctions {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        listener?.setAppTitle(getString(R.string.app_name))

        listener?.hideLoading()

        initListeners()
    }

    override fun initListeners() {

        binding.btnUser.setOnClickListener {

            listener?.gotoFragment(R.id.userFragment)

        }

        binding.btnPost.setOnClickListener {

            listener?.gotoFragment(R.id.postFragment)

        }

        binding.btnPostPaged.setOnClickListener {

            listener?.gotoFragment(R.id.postPagedFragment)

        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (context.applicationContext as MyApplication).appGraph.inject(this)

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
