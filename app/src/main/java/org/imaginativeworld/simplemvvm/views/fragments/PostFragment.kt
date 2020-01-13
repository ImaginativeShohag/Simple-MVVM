package org.imaginativeworld.simplemvvm.views.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.adapters.PostListAdapter
import org.imaginativeworld.simplemvvm.databinding.FragmentPostBinding
import org.imaginativeworld.simplemvvm.db.AppDatabase
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.interfaces.OnFragmentInteractionListener
import org.imaginativeworld.simplemvvm.interfaces.OnObjectListInteractionListener
import org.imaginativeworld.simplemvvm.models.PostResponse
import org.imaginativeworld.simplemvvm.network.ApiClient
import org.imaginativeworld.simplemvvm.repositories.AppRepository
import org.imaginativeworld.simplemvvm.utils.Resource
import org.imaginativeworld.simplemvvm.viewmodels.PostViewModel


class PostFragment : Fragment(), CommonFunctions, OnObjectListInteractionListener<PostResponse> {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var binding: FragmentPostBinding

    private var appViewModel: PostViewModel? = null

    private lateinit var adapter: PostListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Init ViewModel
        activity?.also {
            val appRepository = AppRepository(
                it.applicationContext,
                ApiClient.getClient(),
                AppDatabase(it.applicationContext)
            )

            appViewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return PostViewModel(appRepository) as T
                }
            })[PostViewModel::class.java]
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        listener?.setAppTitle(getString(R.string.title_posts))

        initViews()
        initListeners()

        appViewModel?.getPosts()
    }

    override fun onPause() {
        super.onPause()

        appViewModel?.clearPostObservables()
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

        adapter = PostListAdapter(this)

        binding.recyclerView.adapter = adapter

    }

    override fun initListeners() {

        appViewModel?.getPostsResponse
            ?.observe(this, Observer { resource ->

                when (resource) {

                    is Resource.Success -> {

                        resource.data?.let {

                            adapter.submitList(it)

                            adapter.checkEmptiness()

                        }

                    }

                    is Resource.Error -> {

                        listener?.showSnackbar(resource.message ?: "Error!")

                    }

                }

                resource?.let {
                    listener?.hideLoading()
                }

            })

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

    override fun onClick(position: Int, dataObject: PostResponse) {

        AlertDialog.Builder(this.context)
            .setTitle(dataObject.title)
            .setMessage(dataObject.body)
            .show()

    }

    override fun onLongClick(position: Int, dataObject: PostResponse) {

    }

    override fun showEmptyView() {
        binding.emptyView.emptyLayout.visibility = View.VISIBLE
    }

    override fun hideEmptyView() {
        binding.emptyView.emptyLayout.visibility = View.GONE
    }
}
