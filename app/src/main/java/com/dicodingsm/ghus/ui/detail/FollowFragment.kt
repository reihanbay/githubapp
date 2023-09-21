package com.dicodingsm.ghus.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicodingsm.ghus.databinding.FragmentFollowBinding
import com.dicodingsm.ghus.ui.adapter.UserAdapter
import com.google.android.material.snackbar.Snackbar

class FollowFragment : Fragment() {
    private var binding: FragmentFollowBinding? = null
    private val bind get() = binding!!
    private var username: String = ""
    private var path: String = ""
    private val rvAdapter : UserAdapter by lazy { UserAdapter() }
    private val viewModel: FollowViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getListUserByFollow(username, path)
        with(bind) {
            rvUser.apply {
                adapter = rvAdapter
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                setHasFixedSize(true)
                val decoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
                addItemDecoration(decoration)
            }
        }
        initObservable()
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

    fun setData(user: String, pathFoll: String) {
        path = pathFoll
        username = user
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}