package pk.syyam.githubtrendingrepos.adapters

import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pk.syyam.githubtrendingrepos.databinding.ItemTrendingRepoBinding
import pk.syyam.githubtrendingrepos.model.Items


// PagingDataAdapter — a RecyclerView.Adapter that presents PagingData in a RecyclerView.
// The PagingDataAdapter listens to internal PagingData loading events as pages are loaded
// and uses DiffUtil on a background thread to compute fine-grained updates as updated
// content is received in the form of new PagingData objects.
class TrendingReposAdapter :
    PagingDataAdapter<Items, TrendingReposAdapter.ViewHolder>(RepoDiffCallBack()) {

    class RepoDiffCallBack : DiffUtil.ItemCallback<Items>() {
        override fun areItemsTheSame(oldItem: Items, newItem: Items): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Items, newItem: Items): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(val binding: ItemTrendingRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { repo ->
            with(holder) {
                itemView.tag = repo
                if (repo != null) {
                    binding.name.text = repo.owner.login
                    binding.heading.text = repo.fullName
                    binding.desc.text = repo.description

                    Glide.with(itemView)
                        .load(repo.owner.avatar_url)
                        .centerCrop()
                        .error(android.R.drawable.stat_notify_error)
                        .into(binding.image)

                    binding.lang.text = repo.language
                    binding.star.text = repo.stars.toString()


                    binding.cardView.setOnClickListener {
                        TransitionManager.beginDelayedTransition(binding.cardView)

                        if (binding.layoutDesc.visibility == View.GONE && binding.layoutOther.visibility == View.GONE) {
                            binding.layoutDesc.visibility = View.VISIBLE
                            binding.layoutOther.visibility = View.VISIBLE
                        } else {
                            binding.layoutDesc.visibility = View.GONE
                            binding.layoutOther.visibility = View.GONE
                        }
                    }

                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemTrendingRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
}