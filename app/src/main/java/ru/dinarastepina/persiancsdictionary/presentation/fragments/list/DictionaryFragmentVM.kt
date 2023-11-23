package ru.dinarastepina.persiancsdictionary.presentation.fragments.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import ru.dinarastepina.persiancsdictionary.domain.repository.IDictionaryRepository
import javax.inject.Inject
import androidx.lifecycle.asLiveData

class DictionaryFragmentVM @Inject constructor(
    private val repository: IDictionaryRepository
): ViewModel() {

    val loader = MutableLiveData<Boolean>()

    val words = liveData {
        loader.postValue(true)
        emitSource(repository.getAllArticles()
            .onEach {
                loader.postValue(false)
            }
            .asLiveData())
    }
}