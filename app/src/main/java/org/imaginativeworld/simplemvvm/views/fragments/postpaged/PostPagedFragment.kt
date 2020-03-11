package org.imaginativeworld.simplemvvm.views.fragments.postpaged

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.imaginativeworld.simplemvvm.MyApplication
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.FragmentPostPagedBinding

import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.interfaces.OnDataSourceErrorListener
import org.imaginativeworld.simplemvvm.interfaces.OnFragmentInteractionListener
import org.imaginativeworld.simplemvvm.utils.Constants
import java.lang.Exception
import javax.inject.Inject

class PostPagedFragment : Fragment(), CommonFunctions, OnDataSourceErrorListener {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var binding: FragmentPostPagedBinding

    @Inject
    lateinit var postPagedViewModel: PostPagedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostPagedBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this.viewLifecycleOwner

        binding.userPagedVieModel = postPagedViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listener?.setAppTitle(getString(R.string.title_posts))

        listener?.hideLoading()

        load()
    }

    private fun load() {
        postPagedViewModel.getPostsPaged(
            Constants.SERVER_FORMAT,
            Constants.SERVER_TOKEN,
            this
        )
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

    override fun onError(exception: Exception) {



    }


}
