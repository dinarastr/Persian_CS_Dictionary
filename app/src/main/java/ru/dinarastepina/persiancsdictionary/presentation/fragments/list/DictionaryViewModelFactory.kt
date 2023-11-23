package ru.dinarastepina.persiancsdictionary.presentation.fragments.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import ru.dinarastepina.persiancsdictionary.domain.repository.IDictionaryRepository
import javax.inject.Inject

class DictionaryViewModelFactory @Inject constructor(
    private val repository: IDictionaryRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return DictionaryFragmentVM(repository) as T
    }

}
