package org.imaginativeworld.simplemvvm.views.fragments.demo_user

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.imaginativeworld.simplemvvm.MyApplication
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.adapters.DemoUserListAdapter
import org.imaginativeworld.simplemvvm.databinding.DemoFragmentUserBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.interfaces.OnFragmentInteractionListener
import org.imaginativeworld.simplemvvm.interfaces.OnObjectListInteractionListener
import org.imaginativeworld.simplemvvm.models.DemoUserEntity
import javax.inject.Inject

class DemoUserFragment : Fragment(), CommonFunctions, OnObjectListInteractionListener<DemoUserEntity> {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var binding: DemoFragmentUserBinding

    @Inject
    lateinit var viewModel: DemoUserViewModel

    private lateinit var adapter: DemoUserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()

        adapter = DemoUserListAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DemoFragmentUserBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listener?.setAppTitle(getString(R.string.title_users))

        viewModel.getUsers()

        initViews()

        initListeners()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()

        viewModel.clearUserObservables()
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

    override fun initListeners() {

    }

    override fun initObservers() {

        viewModel.eventShowMessage
            .observe(this, Observer {

                it?.run {

                    listener?.showSnackbar(this)

                }

            })

        viewModel.eventShowLoading
            .observe(this, Observer {

                it?.apply {
                    if (it == true) {
                        listener?.showLoading()
                    } else {
                        listener?.hideLoading()
                    }
                }

            })

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

    override fun onClick(position: Int, dataObject: DemoUserEntity) {

    }

    override fun onLongClick(position: Int, dataObject: DemoUserEntity) {

    }

    override fun showEmptyView() {
        binding.emptyView.emptyLayout.visibility = View.VISIBLE
    }

    override fun hideEmptyView() {
        binding.emptyView.emptyLayout.visibility = View.GONE
    }
}
