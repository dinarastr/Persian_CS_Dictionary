package ru.dinarastepina.persiancsdictionary.presentation.fragments.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import ru.dinarastepina.persiancsdictionary.domain.repository.IDictionaryRepository
import javax.inject.Inject
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.dinarastepina.persiancsdictionary.domain.model.Word
import ru.dinarastepina.persiancsdictionary.presentation.mapper.UIMapper
import ru.dinarastepina.persiancsdictionary.presentation.model.UiWord

@HiltViewModel
class DictionaryFragmentVM @Inject constructor(
    private val repository: IDictionaryRepository,
    private val mapper: UIMapper
): ViewModel() {

    val loader = MutableLiveData<Boolean>()

    val words: LiveData<Result<List<UiWord>>> = liveData {
        loader.postValue(true)
        emitSource(repository.getAllArticles().map { result ->
            result.map { mapper.toUI(it) }
        }
            .onEach {
                loader.postValue(false)
            }
            .asLiveData())
    }
}