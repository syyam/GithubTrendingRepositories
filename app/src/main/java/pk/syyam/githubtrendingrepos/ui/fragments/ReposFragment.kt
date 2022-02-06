package pk.syyam.githubtrendingrepos.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import pk.syyam.githubtrendingrepos.R
import pk.syyam.githubtrendingrepos.adapters.TrendingReposLoadStateAdapter
import pk.syyam.githubtrendingrepos.adapters.TrendingReposAdapter
import pk.syyam.githubtrendingrepos.databinding.FragmentReposBinding
import pk.syyam.githubtrendingrepos.viewmodels.ReposViewModel

@AndroidEntryPoint
class ReposFragment : Fragment(R.layout.fragment_repos) {

    private var binding: FragmentReposBinding? = null

    private val reposViewModel by viewModels<ReposViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        initView(view)

    }

    private fun initView(view: View) {
        binding = FragmentReposBinding.bind(view)

        val adapter = TrendingReposAdapter()


        binding!!.rvRepos.apply {
                setHasFixedSize(true)
                itemAnimator = null
                this.adapter = adapter.withLoadStateHeaderAndFooter(
                    header = TrendingReposLoadStateAdapter { adapter.retry() },
                    footer = TrendingReposLoadStateAdapter { adapter.retry() }
                )
            }

            binding!!.btnRetry.setOnClickListener {
                adapter.retry()
            }


        reposViewModel.repos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding?.apply {
                progress.isVisible = loadState.source.refresh is LoadState.Loading
                rvRepos.isVisible = loadState.source.refresh is LoadState.NotLoading
                llRetry.isVisible = loadState.source.refresh is LoadState.Error

                // no data
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    rvRepos.isVisible = false
                    llRetry.isVisible = true
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}