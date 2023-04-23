package com.sindorim.jetareader.screens.details

import androidx.lifecycle.ViewModel
import com.sindorim.jetareader.data.Resource
import com.sindorim.jetareader.model.Item
import com.sindorim.jetareader.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {

    suspend fun getBookInfo(bookId: String): Resource<Item> {
        return repository.getBookInfo(bookId)
    }

} // End of DetailsViewModel