package ru.dinarastepina.persiancsdictionary.presentation.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.dinarastepina.persiancsdictionary.databinding.PagingLoaderBinding

class PagingLoadStateAdapter(
    private val retry: () -> Unit
): LoadStateAdapter<PagingLoadStateAdapter.LoaderViewHolder>() {
    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoaderViewHolder {
        return LoaderViewHolder(
            PagingLoaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ) {
            retry()
        }
    }

     class LoaderViewHolder(val binding: PagingLoaderBinding, retry: () -> Unit): RecyclerView.ViewHolder(binding.root) {

         fun bind(loadState: LoadState) {
             if (loadState is LoadState.Error) {
                 binding.errorTv.text = loadState.error.localizedMessage
             }
             binding.pb.visibility = toVisibility(loadState is LoadState.Loading)
             binding.errorTv.visibility = toVisibility(loadState !is LoadState.Loading)
         }

         private fun toVisibility(constraint: Boolean): Int = if (constraint) {
             View.VISIBLE
         } else {
             View.GONE
         }
     }
}