package ru.dinarastepina.persiancsdictionary.presentation.fragments.list

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.text.set
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
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

    private val dictionaryAdapter: MyDictionaryRecyclerViewAdapter by lazy {
        MyDictionaryRecyclerViewAdapter(
            listener = { id ->
                val action =
                    DictionaryFragmentDirections.actionDictionaryFragmentToDetailsFragment(id)
                findNavController().navigate(action)
            }
        )
    }

    private val loadStateAdapter: PagingLoadStateAdapter by lazy {
        PagingLoadStateAdapter {
           Log.i("hghghjgjhhjhj", "gghghghj")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _vb = FragmentDictionaryBinding.inflate(layoutInflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//            viewmodel.loader.observe(viewLifecycleOwner) { visible ->
//                if (visible) {
//                    vb.progressBar.visibility = View.VISIBLE
//                } else {
//                    vb.progressBar.visibility = View.GONE
//                }
        //}

        updateSearch()


        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.words.collect { result ->
                setUpAdapter( result)
            }
        }
    }

    private suspend fun setUpAdapter(words: PagingData<UiWord>) {
        with(vb.dictionaryRv) {
            adapter = dictionaryAdapter.withLoadStateFooter(loadStateAdapter)
        }
        dictionaryAdapter.submitData(
            words
        )
    }

    private fun initSearch() {
        vb.searchView.text?.trim()?.let {
            if (it.isNotEmpty()) {
                viewmodel.update(it)
            }
        }
    }

    private fun updateSearch() {
        vb.searchView.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                initSearch()
                true
            } else {
                false
            }
        }

        vb.searchView.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                initSearch()
                true
            } else {
                false
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _vb = null
    }
}