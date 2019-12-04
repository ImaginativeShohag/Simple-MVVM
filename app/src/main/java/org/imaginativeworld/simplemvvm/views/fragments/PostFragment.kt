package org.imaginativeworld.simplemvvm.views.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_post.*
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.adapters.PostListAdapter
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.interfaces.OnFragmentInteractionListener
import org.imaginativeworld.simplemvvm.interfaces.OnObjectListInteractionListener
import org.imaginativeworld.simplemvvm.models.PostResponse
import org.imaginativeworld.simplemvvm.models.UserEntity
import org.imaginativeworld.simplemvvm.utils.Resource
import org.imaginativeworld.simplemvvm.viewmodels.AppViewModel
import kotlin.random.Random


class PostFragment : Fragment(), CommonFunctions, OnObjectListInteractionListener<PostResponse> {

    private var listener: OnFragmentInteractionListener? = null

    private var appViewModel: AppViewModel? = null

    private lateinit var adapter: PostListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let {
            appViewModel = ViewModelProviders.of(it).get(AppViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onResume() {
        super.onResume()

        listener?.setAppTitle(getString(R.string.title_posts))

        initViews()
        initListeners()

        appViewModel?.getPosts()
    }

    override fun initViews() {

        // List
        val layoutManager = LinearLayoutManager(activity)
        recycler_view.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(
            recycler_view.context,
            layoutManager.orientation
        )
        recycler_view.addItemDecoration(dividerItemDecoration)

        adapter = PostListAdapter(context!!, this)
        recycler_view.adapter = adapter

    }

    override fun initListeners() {

        appViewModel?.getPostsResponse
            ?.observe(this, Observer { resource ->

                when (resource) {

                    is Resource.Success -> {

                        resource.data?.let {

                            adapter.addAll(it)

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

    }

    override fun onLongClick(position: Int, dataObject: PostResponse) {

    }

    override fun showEmptyView() {

    }

    override fun hideEmptyView() {

    }
}
