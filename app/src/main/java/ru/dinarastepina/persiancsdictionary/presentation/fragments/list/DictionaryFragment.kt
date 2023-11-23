package ru.dinarastepina.persiancsdictionary.presentation.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
    lateinit var viewmodel: DictionaryFragmentVM
    @Inject
    lateinit var viewmodelFactory: DictionaryViewModelFactory
    private var _vb: FragmentDictionaryBinding? = null
    private val vb: FragmentDictionaryBinding
        get() = _vb!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _vb = FragmentDictionaryBinding.inflate(layoutInflater, container, false)

        setUpViewModel()

        viewmodel.loader.observe(viewLifecycleOwner) { visible ->
            if (visible) {
                vb.progressBar.visibility = View.VISIBLE
            } else {
                vb.progressBar.visibility = View.GONE
            }
        }

        viewmodel.words.observe(viewLifecycleOwner) { result ->
            result.getOrNull()?.let { words ->
                setUpAdapter(words)
            } ?: println()
        }
        return vb.root
    }

    private fun setUpAdapter(words: List<UiWord>) {
        with(vb.dictionaryRv) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyDictionaryRecyclerViewAdapter(words) {id ->
                val action = DictionaryFragmentDirections.actionDictionaryFragmentToDetailsFragment(id)
                findNavController().navigate(action)
            }
        }
    }

    private fun setUpViewModel() {
        viewmodel = ViewModelProvider(this, viewmodelFactory)[DictionaryFragmentVM::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vb = null
    }
}