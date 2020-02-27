package org.imaginativeworld.simplemvvm.views.fragments.user

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.imaginativeworld.simplemvvm.MyApplication
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.adapters.UserListAdapter
import org.imaginativeworld.simplemvvm.databinding.FragmentUserBinding
import org.imaginativeworld.simplemvvm.db.AppDatabase
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.interfaces.OnFragmentInteractionListener
import org.imaginativeworld.simplemvvm.interfaces.OnObjectListInteractionListener
import org.imaginativeworld.simplemvvm.models.UserEntity
import org.imaginativeworld.simplemvvm.network.ApiClient
import org.imaginativeworld.simplemvvm.repositories.AppRepository
import javax.inject.Inject

class UserFragment : Fragment(), CommonFunctions, OnObjectListInteractionListener<UserEntity> {

    private val TAG = "UserFragment"

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var binding: FragmentUserBinding

    @Inject
    lateinit var userViewModel: UserViewModel

    private lateinit var adapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()

        adapter = UserListAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this.viewLifecycleOwner

        binding.userViewModel = userViewModel

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        listener?.setAppTitle(getString(R.string.title_users))

        initViews()
        initListeners()

        userViewModel?.getUsers()
    }

    override fun onPause() {
        super.onPause()

        userViewModel?.clearUserObservables()
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

        userViewModel.eventShowMessage
            .observe(this, Observer {

                it?.run {

                    listener?.showSnackbar(this)

                }

            })

        userViewModel.eventShowLoading
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

    override fun onClick(position: Int, dataObject: UserEntity) {

    }

    override fun onLongClick(position: Int, dataObject: UserEntity) {

    }

    override fun showEmptyView() {
        binding.emptyView.emptyLayout.visibility = View.VISIBLE
    }

    override fun hideEmptyView() {
        binding.emptyView.emptyLayout.visibility = View.GONE
    }
}
