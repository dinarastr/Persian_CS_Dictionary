package ru.dinarastepina.persiancsdictionary.presentation.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.dinarastepina.persiancsdictionary.databinding.WordItemBinding
import ru.dinarastepina.persiancsdictionary.presentation.model.UiWord

class MyDictionaryRecyclerViewAdapter(
    private val values: List<UiWord>,
    private val listener: (String) -> Unit
) : RecyclerView.Adapter<MyDictionaryRecyclerViewAdapter.ViewHolder>() {

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
        val item = values[position]
        holder.english.text = item.word
        holder.persian.text = item.translations.joinToString(",\n")
        holder.english.setOnClickListener {
            listener(item.id)
        }
    }

    override fun getItemCount(): Int =
        values.size

    inner class ViewHolder(binding: WordItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val english: TextView = binding.englishTv
        val persian: TextView = binding.persianTv
    }
}