package ru.dinarastepina.persiancsdictionary.presentation.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.dinarastepina.persiancsdictionary.databinding.WordItemBinding
import ru.dinarastepina.persiancsdictionary.presentation.model.UiWord

class MyDictionaryRecyclerViewAdapter(
    private val listener: (String) -> Unit
) : PagingDataAdapter<UiWord, MyDictionaryRecyclerViewAdapter.ViewHolder>(
    REPO_COMPARATOR
) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<UiWord>() {
            override fun areItemsTheSame(oldItem: UiWord, newItem: UiWord): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: UiWord, newItem: UiWord): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            WordItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.english.text = item.word
        holder.persian.text = item.translations.joinToString(",\n")
        holder.english.setOnClickListener {
            listener(item.id)
        }
    }

    inner class ViewHolder(binding: WordItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val english: TextView = binding.englishTv
        val persian: TextView = binding.persianTv
    }
}