package ru.dinarastepina.persiancsdictionary.presentation.fragments.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.dinarastepina.persiancsdictionary.domain.repository.IDictionaryRepository
import ru.dinarastepina.persiancsdictionary.presentation.mapper.UIMapper
import javax.inject.Inject

@HiltViewModel
class DictionaryFragmentVM @Inject constructor(
    private val repository: IDictionaryRepository,
    private val mapper: UIMapper
): ViewModel() {

    val loader = MutableLiveData<Boolean>()

    val words = repository.getAllArticles().flowOn(Dispatchers.IO)
        .map {
                data ->
           data.map { mapper.toUI(it) }
        }
        .cachedIn(viewModelScope)
}