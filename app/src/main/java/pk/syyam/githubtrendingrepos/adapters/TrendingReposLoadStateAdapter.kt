package pk.syyam.githubtrendingrepos.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import pk.syyam.githubtrendingrepos.databinding.TrendingReposLoadStateBinding

class TrendingReposLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<TrendingReposLoadStateAdapter.LoadStateViewHolder>(){

    // The ReposLoadStateAdapter class receives a retry function, it will be called when the retry button is pressed in the onBindViewHolder().

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = TrendingReposLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(private val binding: TrendingReposLoadStateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener{
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progress.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState !is LoadState.Loading
                error.isVisible = loadState !is LoadState.Loading
            }
        }

    }

}