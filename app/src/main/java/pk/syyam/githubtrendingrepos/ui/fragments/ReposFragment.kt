package pk.syyam.githubtrendingrepos.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint
import pk.syyam.githubtrendingrepos.R
import pk.syyam.githubtrendingrepos.adapters.TrendingReposAdapter
import pk.syyam.githubtrendingrepos.adapters.TrendingReposLoadStateAdapter
import pk.syyam.githubtrendingrepos.databinding.FragmentReposBinding
import pk.syyam.githubtrendingrepos.viewmodels.ReposViewModel
import javax.inject.Inject


@AndroidEntryPoint
class ReposFragment : Fragment(R.layout.fragment_repos) {
    @Inject
    lateinit var sharedPreferences: SharedPreferences

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


    override fun onResume() {
        super.onResume()
        binding!!.shimmerViewContainer.startShimmer()
    }

    override fun onPause() {
        binding!!.shimmerViewContainer.stopShimmer()
        super.onPause()
    }

    private fun initView(view: View) {
        binding = FragmentReposBinding.bind(view)
        setHasOptionsMenu(true)
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
                shimmerViewContainer.isVisible = loadState.source.refresh is LoadState.Loading
                rvRepos.isVisible = loadState.source.refresh is LoadState.NotLoading
                llRetry.isVisible = loadState.source.refresh is LoadState.Error

                // no data
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    rvRepos.isVisible = false
                    llRetry.isVisible = true
                    shimmerViewContainer.isVisible = false
                    shimmerViewContainer.stopShimmer()

                } else {
                    shimmerViewContainer.stopShimmer()

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}