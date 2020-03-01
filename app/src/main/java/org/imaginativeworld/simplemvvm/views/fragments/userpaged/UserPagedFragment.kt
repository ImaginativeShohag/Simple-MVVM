package org.imaginativeworld.simplemvvm.views.fragments.userpaged

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import org.imaginativeworld.simplemvvm.MyApplication

import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.interfaces.OnFragmentInteractionListener

class UserPagedFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var viewModel: UserPagedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_paged_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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
