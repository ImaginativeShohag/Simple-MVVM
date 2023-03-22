package org.imaginativeworld.simplemvvm.ui.fragments.demo_fragment_nav

import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.FragmentDemoFragmentNavListBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions
import org.imaginativeworld.simplemvvm.ui.fragments.demo_fragment_nav.viewmodel.DemoFragmentNavViewModel
import timber.log.Timber

class DemoFragmentNavList : Fragment(R.layout.fragment_demo_fragment_nav_list), CommonFunctions {

    private val parentViewModel: DemoFragmentNavViewModel by viewModels(ownerProducer = {
        requireParentFragment()
    })

    private lateinit var binding: FragmentDemoFragmentNavListBinding

    private var items = listOf(
        "🍎 Red Apple",
        "🍏 Green Apple",
        "🍊 Orange",
        "🍌 Banana",
        "🥭 Mango",
        "🍐 Papaya",
        "🍍 Pineapple",
        "🍈 Melon",
        "🍉 Watermelon",
        "🍊 Tangerine",
        "🍋 Lemon",
        "🍐 Pear",
        "🍑 Peach",
        "🍒 Cherries",
        "🍓 Strawberry",
        "🫐 Blueberries",
        "🥝 Kiwi Fruit"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentDemoFragmentNavListBinding.bind(view)

        // Pass data between fragments: using `Bundle`.
        arguments?.apply {
            val dataOne = getInt("data_one")
            Timber.d("dataOne: $dataOne")
        }

        initObservers()

        initViews()

        initListeners()
    }

    override fun initObservers() {
        // Pass data between fragments: using `FragmentManager`.
        setFragmentResultListener(REQUEST_KEY) { _, bundle ->
            val action = bundle.getString(PARAM_ACTION)

            if (action == PARAM_ACTION_SHUFFLE) {
                items = items.shuffled()

                initAdapter()
            }
        }
    }

    override fun initViews() {
        initAdapter()
    }

    private fun initAdapter() {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)
        binding.list.adapter = adapter
    }

    override fun initListeners() {
        binding.list.onItemClickListener = OnItemClickListener { _, _, position, _ ->
            parentViewModel.selectItem(
                items[position]
            )
        }
    }

    companion object {
        const val PARAM_ACTION = "action"
        const val PARAM_ACTION_SHUFFLE = "shuffle"

        const val REQUEST_KEY = "request_key_nav_list"
    }
}
