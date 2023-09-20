package com.dicodingsm.ghus.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.dicodingsm.ghus.R
import com.dicodingsm.ghus.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var binding : FragmentMainBinding? = null
    private val bind get() = binding!!
    private val viewModel: MainViewModel by viewModels()
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
                false
            }
        }
        initObservable()
    }

    private fun initObservable() {
        viewModel.listUser.observe(viewLifecycleOwner){
            Log.d("TAG", "initObservable: $it")
        }
        viewModel.isLoading.observe(viewLifecycleOwner){
            bind.progressHorizontal.isVisible = it
            bind.rvUser.isVisible = !it
        }
    }

}