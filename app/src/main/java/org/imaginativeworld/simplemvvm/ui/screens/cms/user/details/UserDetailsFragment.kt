package org.imaginativeworld.simplemvvm.ui.screens.cms.user.details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.FragmentCmsUserDetailsBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.ui.screens.cms.CMSMainViewModel

@AndroidEntryPoint
class UserDetailsFragment : Fragment(R.layout.fragment_cms_user_details), CommonFunctions {
    private val args: UserDetailsFragmentArgs by navArgs()

    private lateinit var binding: FragmentCmsUserDetailsBinding

    private val viewModel: UserDetailsViewModel by viewModels()
    private val parentViewModel: CMSMainViewModel by viewModels(ownerProducer = {
        requireActivity()
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCmsUserDetailsBinding.bind(view)

        initViews()
        initListeners()
        getDetails()
    }

    override fun initObservers() {
        viewModel.eventShowMessage.observe(this) {
            it?.run {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.eventShowLoading.observe(this) {
            it?.run {
                if (this) {
                    parentViewModel.showLoading()
                } else {
                    parentViewModel.hideLoading()
                }
            }
        }

        viewModel.todo.observe(this) { user ->
            user?.let {
                binding.tvId.text = "${user.id}"
                binding.tvName.text = user.name
                binding.tvEmail.text = user.email
                binding.tvGender.text = user.gender
                binding.tvStatus.text = user.getStatusLabel()
                binding.root.context?.let { context ->
                    binding.tvStatus.setTextColor(
                        ContextCompat.getColor(
                            context,
                            user.getStatusColor()
                        )
                    )
                }
            }
        }

        viewModel.eventDeleteSuccess.observe(this) { isSuccess ->
            if (isSuccess == true) {
                findNavController().popBackStack()
            }
        }
    }

    override fun initViews() {
        binding.actionBar.tvActionTitle.text = "User Details"
    }

    override fun initListeners() {
        binding.actionBar.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnDelete.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Are you sure you want to delete this todo?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes") { _, _ ->
                    viewModel.delete(args.userId)
                }
                .show()
        }

        binding.btnEdit.setOnClickListener {
            val action =
                UserDetailsFragmentDirections.actionUserDetailsFragmentToUserEditFragment(
                    args.userId
                )
            findNavController().navigate(action)
        }

        binding.btnTodos.setOnClickListener {
            val action =
                UserDetailsFragmentDirections.actionUserDetailsFragmentToTodoListFragment(
                    args.userId
                )
            findNavController().navigate(action)
        }

        binding.btnPosts.setOnClickListener {
            val action =
                UserDetailsFragmentDirections.actionUserDetailsFragmentToPostListFragment(
                    args.userId
                )
            findNavController().navigate(action)
        }
    }

    private fun getDetails() {
        viewModel.getDetails(args.userId)
    }
}
