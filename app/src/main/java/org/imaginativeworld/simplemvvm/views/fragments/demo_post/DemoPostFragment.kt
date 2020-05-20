package org.imaginativeworld.simplemvvm.views.fragments.demo_post

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.imaginativeworld.simplemvvm.MyApplication
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.adapters.DemoPostListAdapter
import org.imaginativeworld.simplemvvm.databinding.DemoFragmentPostBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.interfaces.OnFragmentInteractionListener
import org.imaginativeworld.simplemvvm.interfaces.OnObjectListInteractionListener
import org.imaginativeworld.simplemvvm.models.DemoPostResult
import org.imaginativeworld.simplemvvm.utils.Constants
import timber.log.Timber
import javax.inject.Inject

class DemoPostFragment : Fragment(), CommonFunctions, OnObjectListInteractionListener<DemoPostResult> {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var binding: DemoFragmentPostBinding

    @Inject
    lateinit var viewModel: DemoPostViewModel

    private lateinit var adapter: DemoPostListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")

        initObservers()

        adapter = DemoPostListAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("onCreateView")

        binding = DemoFragmentPostBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listener?.setAppTitle(getString(R.string.title_posts))

        listener?.hideLoading()

        initViews()

        load()
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")
    }

    private fun load() {
        viewModel.getPosts(
            Constants.SERVER_FORMAT,
            Constants.SERVER_TOKEN
        )
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause")
    }

    override fun initViews() {

        // List
        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(
            binding.recyclerView.context,
            layoutManager.orientation
        )
        binding.recyclerView.addItemDecoration(dividerItemDecoration)

        binding.recyclerView.adapter = adapter

    }

    override fun initObservers() {

        viewModel.eventShowMessage
            .observe(this, Observer {

                it?.run {

                    listener?.showSnackbar(this, "Retry") {

                        load()

                    }

                }

            })

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.d("onAttach")

        (context.applicationContext as MyApplication).appGraph.inject(this)

        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        Timber.d("onDetach")

        listener = null
    }

    override fun onClick(position: Int, dataObject: DemoPostResult) {

        this.context?.apply {
            AlertDialog.Builder(this)
                .setTitle(dataObject.title)
                .setMessage(dataObject.body)
                .show()
        }

    }

    override fun onLongClick(position: Int, dataObject: DemoPostResult) {

    }

    override fun showEmptyView() {
        binding.emptyView.emptyLayout.visibility = View.VISIBLE
    }

    override fun hideEmptyView() {
        binding.emptyView.emptyLayout.visibility = View.GONE
    }
}
