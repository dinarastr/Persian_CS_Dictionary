package ru.dinarastepina.persiancsdictionary.presentation.fragments.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import ru.dinarastepina.persiancsdictionary.domain.repository.IDictionaryRepository
import ru.dinarastepina.persiancsdictionary.presentation.mapper.UIMapper
import javax.inject.Inject

@HiltViewModel
class DictionaryFragmentVM @Inject constructor(
    private val repository: IDictionaryRepository,
    private val mapper: UIMapper
): ViewModel() {

    private val _query: MutableStateFlow<String> = MutableStateFlow("")
    val query: StateFlow<String>
        get() = _query.asStateFlow()

    fun update(it: CharSequence) {
        _query.value = it.toString()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    val search = _query.flatMapLatest {
        repository.searchArticles(
            it
        ).map { data ->
            data.map { mapper.toUI(it) }
        }.cachedIn(viewModelScope)
    }

    val loader = MutableLiveData<Boolean>()

    val words = repository.getAllArticles()
        .map {
                data ->
           data.map { mapper.toUI(it) }
        }

}