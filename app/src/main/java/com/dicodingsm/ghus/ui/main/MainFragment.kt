package com.dicodingsm.ghus.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicodingsm.ghus.databinding.FragmentMainBinding
import com.dicodingsm.ghus.ui.adapter.UserAdapter
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    private var binding : FragmentMainBinding? = null
    private val bind get() = binding!!
    private val viewModel: MainViewModel by viewModels()
    private val rvAdapter : UserAdapter by lazy { UserAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(bind) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { textView, i, keyEvent ->
                searchBar.text = textView.text
                //doActionViewModel
                viewModel.searchListUser(textView.text.toString())
                searchView.hide()
                false
            }

            searchView.editText.addTextChangedListener {
                searchBar.text = it.toString()
                //doActionViewModel
                if (it.toString().isEmpty()) {
                    viewModel.getListUser()
                } else {
                    viewModel.searchListUser(it.toString())
                }
            }

            rvUser.apply {
                adapter = rvAdapter
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                setHasFixedSize(true)
                val decoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
                addItemDecoration(decoration)
            }

        }
        initObservable()
        initAction()
    }
    private fun initAction() {
        rvAdapter.setClickListener(object : UserAdapter.setOnClickListener {
            override fun setOnClicked(username: String) {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailFragment(username))
            }
        })
    }

    private fun initObservable() {
        viewModel.message.observe(viewLifecycleOwner) {
            Snackbar.make(bind.root, it, Snackbar.LENGTH_SHORT).show()
        }

        viewModel.listUser.observe(viewLifecycleOwner){
            rvAdapter.setList(it)
        }
        viewModel.isLoading.observe(viewLifecycleOwner){
            bind.progressHorizontal.isVisible = it
            bind.rvUser.isVisible = !it
        }
    }

}