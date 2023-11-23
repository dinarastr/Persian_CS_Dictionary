package ru.dinarastepina.persiancsdictionary.presentation.fragments.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.dinarastepina.persiancsdictionary.R
import ru.dinarastepina.persiancsdictionary.databinding.FragmentDictionaryBinding
import ru.dinarastepina.persiancsdictionary.presentation.model.UiWord
import javax.inject.Inject

@AndroidEntryPoint
class DictionaryFragment : Fragment() {
    private val viewmodel: DictionaryFragmentVM by viewModels()
    private var _vb: FragmentDictionaryBinding? = null
    private val vb: FragmentDictionaryBinding
        get() = _vb!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _vb = FragmentDictionaryBinding.inflate(layoutInflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.loader.observe(viewLifecycleOwner) { visible ->
            if (visible) {
                vb.progressBar.visibility = View.VISIBLE
            } else {
                vb.progressBar.visibility = View.GONE
            }
        }

        viewmodel.words.observe(viewLifecycleOwner) { result ->
            result.getOrNull()?.let { words ->
                Log.i("dinara", words.toString())
                setUpAdapter(words)
            } ?: Log.i("dinara", "empty")
        }
    }

    private fun setUpAdapter(words: List<UiWord>) {
        with(vb.dictionaryRv) {
            adapter = MyDictionaryRecyclerViewAdapter(words) {id ->
                val action = DictionaryFragmentDirections.actionDictionaryFragmentToDetailsFragment(id)
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vb = null
    }
}