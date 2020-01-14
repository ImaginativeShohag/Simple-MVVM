package org.imaginativeworld.simplemvvm.views.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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
import org.imaginativeworld.simplemvvm.viewmodels.UserViewModel
import timber.log.Timber
import kotlin.random.Random

class UserFragment : Fragment(), CommonFunctions, OnObjectListInteractionListener<UserEntity> {

    private val TAG = "UserFragment"

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var binding: FragmentUserBinding

    private var appViewModel: UserViewModel? = null

    private lateinit var adapter: UserListAdapter

    private val totalData = 4

    private val names = listOf(
        "Shohag",
        "Rakib",
        "Shafee",
        "Imran"
    )

    private val phones = listOf(
        "01111111111",
        "02222222222",
        "03333333333",
        "04444444444"
    )

    private val images = listOf(
        "http://teamshunno.com/wp-content/uploads/2018/07/Sohag-278x300.jpg",
        "http://teamshunno.com/wp-content/uploads/2018/07/Rakib-1-283x300.jpg",
        "http://teamshunno.com/wp-content/uploads/2018/07/Shafee_new-3-300x300.png",
        "http://teamshunno.com/wp-content/uploads/2018/07/imran-1-285x300.jpg"
    )

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
                    return UserViewModel(appRepository) as T
                }
            })[UserViewModel::class.java]
        }

        adapter = UserListAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        listener?.setAppTitle(getString(R.string.title_users))

        initViews()
        initListeners()

        appViewModel?.getUsers()
    }

    override fun onPause() {
        super.onPause()

        appViewModel?.clearUserObservables()
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

        binding.fabAdd.setOnClickListener {

            val randomIndex = Random.nextInt(totalData)

            appViewModel?.addUser(
                UserEntity(
                    name = names[randomIndex],
                    phone = phones[randomIndex],
                    image = images[randomIndex]
                )
            )

            listener?.showLoading()

        }

        binding.fabClear.setOnClickListener {

            appViewModel?.removeAllUsers()

        }

        appViewModel?.removeAllUsersResponse
            ?.observe(this, Observer { resource ->

                resource?.data?.let { success ->

                    if (success) {
                        appViewModel?.getUsers()

                        appViewModel?.removeAllUsersResponse?.value = null
                    }

                }

                resource?.let {
                    listener?.hideLoading()
                }
            })

        appViewModel?.addUserResponse
            ?.observe(this, Observer { resource ->

                resource?.data?.let { insertId ->

                    Timber.e("insertId: $insertId")

                    appViewModel?.getUsers()

                }

                resource?.let {
                    listener?.hideLoading()
                }

            })

        appViewModel?.getUsersResponse
            ?.observe(this, Observer { resource ->

                resource?.data?.let {

                    adapter.submitList(it)

                    if (it.isEmpty()) {
                        showEmptyView()
                    } else {
                        hideEmptyView()
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
