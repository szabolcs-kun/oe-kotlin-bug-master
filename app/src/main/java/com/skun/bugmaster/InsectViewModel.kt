package com.skun.bugmaster

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.net.URL

class InsectViewModel (
    private val dataSource: InsectDao
) : ViewModel() {

    private var _insects = dataSource.get()
    val insects = _insects

    init {
        loadMoviesFromDatabase()
    }

    /**
     * Load data from SQLite database and show on the screen
     */
    private fun loadMoviesFromDatabase() {
        _insects = dataSource.get()
    }
}