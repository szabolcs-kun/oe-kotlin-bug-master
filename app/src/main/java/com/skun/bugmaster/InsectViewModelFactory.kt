package com.skun.bugmaster

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class InsectViewModelFactory(
    private val dataSource: InsectDao
) : ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InsectViewModel::class.java)) {
            return InsectViewModelFactory(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}