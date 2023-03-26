package org.imaginativeworld.simplemvvm.ui.screens.awesometodos.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.FragmentAwesomeTodosAddBinding
import org.imaginativeworld.simplemvvm.interfaces.CommonFunctions

class TodoAddFragment : Fragment(R.layout.fragment_awesome_todos_add), CommonFunctions {
    private lateinit var binding: FragmentAwesomeTodosAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAwesomeTodosAddBinding.bind(view)

        initViews()

        initListeners()
    }

    override fun initObservers() {
        /* no-op */
    }

    override fun initViews() {
        /* no-op */
    }

    override fun initListeners() {
        /* no-op */
    }
}
