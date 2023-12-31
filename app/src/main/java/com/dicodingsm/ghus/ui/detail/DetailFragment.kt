package com.dicodingsm.ghus.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.dicodingsm.ghus.R
import com.dicodingsm.ghus.databinding.FragmentDetailBinding
import com.dicodingsm.ghus.ui.adapter.SectionPagerAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import java.util.Locale

class DetailFragment : Fragment() {
    private lateinit var bind : FragmentDetailBinding
    private val args : DetailFragmentArgs by navArgs()
    private val viewModel : DetailViewModel by viewModels()
    private lateinit var vpAdapter : SectionPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        bind = FragmentDetailBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDetailUser(args.data)
        initObservable()

    }

    private fun initViewPager(count: List<Int?>) {
        val title = listOf("followers", "following")
        val listFragment= mutableListOf<Fragment>()
        title.forEach {
            val fragment = FollowFragment()
            fragment.setData(args.data, it)
            listFragment.add(fragment)
        }
        vpAdapter = SectionPagerAdapter(listFragment, childFragmentManager, lifecycle)
        bind.vpListFollow.adapter = vpAdapter
        TabLayoutMediator(bind.tabLayout, bind.vpListFollow) { tab, position ->
            tab.text = "${count[position]?:""} ${title[position].capitalize(Locale.ROOT)}"
        }.attach()
    }

    private fun initObservable() {

        viewModel.message.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) Snackbar.make(bind.root, it, Snackbar.LENGTH_SHORT).show()
        }
        viewModel.isLoading.observe(viewLifecycleOwner){
            bind.progressHorizontal.isVisible = it
        }

        viewModel.user.observe(viewLifecycleOwner) {
            with(bind){
                vpListFollow.isVisible = true
                Glide.with(requireContext()).load(it.avatarUrl).into(civUser)
                tvUsername.text = getString(R.string.text_username, it.name, it.login)
                tvCompany.text = it.company ?: "Belum Diatur"
            }
            val list = listOf(it.followers, it.following)
            initViewPager(list)
        }
    }

}