package org.imaginativeworld.simplemvvm.ui.fragments.demo_postpaged

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.adapters.DemoPostPagedListAdapter
import org.imaginativeworld.simplemvvm.databinding.DemoFragmentPostPagedBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.interfaces.OnDataSourceErrorListener
import org.imaginativeworld.simplemvvm.interfaces.OnFragmentInteractionListener
import org.imaginativeworld.simplemvvm.interfaces.OnObjectListInteractionListener
import org.imaginativeworld.simplemvvm.models.DemoPostResult
import org.imaginativeworld.simplemvvm.utils.Constants

@AndroidEntryPoint
class DemoPostPagedFragment : Fragment(), CommonFunctions, OnDataSourceErrorListener,
    OnObjectListInteractionListener<DemoPostResult> {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var binding: DemoFragmentPostPagedBinding

    private val viewModel: DemoPostPagedViewModel by viewModels()

    private lateinit var adapter: DemoPostPagedListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()

        adapter = DemoPostPagedListAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DemoFragmentPostPagedBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listener?.setAppTitle(getString(R.string.title_posts))

        listener?.hideLoading()

        initViews()

        initListeners()
    }

    override fun onResume() {
        super.onResume()

        load()
    }

    private fun load() {
        viewModel.getPostsPaged(
            Constants.SERVER_FORMAT,
            Constants.SERVER_TOKEN,
            this
        )
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

    override fun onDataSourceError(exception: Exception) {
        activity?.runOnUiThread {
            listener?.hideLoading()

            listener?.showSnackbar(exception.message ?: "Unknown Error!")
        }
    }

    override fun onClick(position: Int, dataObject: DemoPostResult) {

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
