package ru.dinarastepina.persiancsdictionary.presentation.fragments.list

import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView

class PagingLoadStateAdapter(
    private val retry: () -> Unit
): LoadStateAdapter<PagingLoadStateAdapter.LoaderViewHolder>() {
    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoaderViewHolder {
        TODO("Not yet implemented")
    }

     class LoaderViewHolder(view: View, retry: () -> Unit): RecyclerView.ViewHolder(view) {

    }
}