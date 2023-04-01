package org.imaginativeworld.simplemvvm.ui.screens.demo_post

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.adapters.DemoPostListAdapter
import org.imaginativeworld.simplemvvm.databinding.DemoFragmentPostBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.interfaces.OnFragmentInteractionListener
import org.imaginativeworld.simplemvvm.interfaces.OnObjectListInteractionListener
import org.imaginativeworld.simplemvvm.models.DemoPost
import timber.log.Timber

@AndroidEntryPoint
class DemoPostFragment :
    Fragment(),
    CommonFunctions,
    OnObjectListInteractionListener<DemoPost> {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var binding: DemoFragmentPostBinding

    private val viewModel: DemoPostViewModel by viewModels()

    private lateinit var adapter: DemoPostListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")

        initObservers()

        adapter = DemoPostListAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        viewModel.getPosts()
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
            .observe(this) {
                it?.run {
                    listener?.showSnackbar(this, "Retry") {
                        load()
                    }
                }
            }

        viewModel.eventShowLoading
            .observe(this) {
                it?.run {
                    if (this) {
                        listener?.showLoading()
                    } else {
                        listener?.hideLoading()
                    }
                }
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.d("onAttach")

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

    override fun onClick(position: Int, dataObject: DemoPost) {
        this.context?.apply {
            AlertDialog.Builder(this)
                .setTitle(dataObject.title)
                .setMessage(dataObject.body)
                .show()
        }
    }

    override fun onLongClick(position: Int, dataObject: DemoPost) {
        /* no-op */
    }

    override fun showEmptyView() {
        binding.emptyView.emptyLayout.visibility = View.VISIBLE
    }

    override fun hideEmptyView() {
        binding.emptyView.emptyLayout.visibility = View.GONE
    }
}
