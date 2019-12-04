package org.imaginativeworld.simplemvvm.views.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.android.synthetic.main.fragment_post.recycler_view
import kotlinx.android.synthetic.main.fragment_user.*
import org.imaginativeworld.simplemvvm.interfaces.OnFragmentInteractionListener

import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.adapters.PostListAdapter
import org.imaginativeworld.simplemvvm.adapters.UserListAdapter
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.interfaces.OnObjectListInteractionListener
import org.imaginativeworld.simplemvvm.models.UserEntity
import org.imaginativeworld.simplemvvm.viewmodels.AppViewModel
import kotlin.random.Random

class UserFragment : Fragment(), CommonFunctions, OnObjectListInteractionListener<UserEntity> {

    private val TAG = "UserFragment"

    private var listener: OnFragmentInteractionListener? = null

    private var appViewModel: AppViewModel? = null

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

        activity?.let {
            appViewModel = ViewModelProviders.of(it).get(AppViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onResume() {
        super.onResume()

        listener?.setAppTitle(getString(R.string.title_users))

        initViews()
        initListeners()

        appViewModel?.getUsers()
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

        adapter = UserListAdapter(context!!, this)
        recycler_view.adapter = adapter

    }

    override fun initListeners() {

        floating_action_button.setOnClickListener {

            val randomIndex = Random.nextInt(totalData)

            appViewModel?.addUser(
                UserEntity(
                    name = names[randomIndex],
                    phone = phones[randomIndex],
                    image = images[randomIndex]
                )
            )

        }

        appViewModel?.addUserResponse
            ?.observe(this, Observer { resource ->

                resource?.data?.let { insertId ->

                    Log.e(TAG, "insertId: $insertId")

                    appViewModel?.getUsers()

                }

            })

        appViewModel?.getUsersResponse
            ?.observe(this, Observer { resource ->

                resource?.data?.let {

                    adapter.addAll(it)

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

    }

    override fun hideEmptyView() {

    }
}
