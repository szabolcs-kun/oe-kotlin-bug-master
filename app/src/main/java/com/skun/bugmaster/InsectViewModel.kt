package com.skun.bugmaster

import androidx.lifecycle.ViewModel

class InsectViewModel (
    private val dataSource: InsectDao
) : ViewModel() {

    private var _insects = dataSource.get()
    val insects = _insects

    init {
        loadInsectsFromDatabase()
    }

    /**
     * Load data from SQLite database and show on the screen
     */
    private fun loadInsectsFromDatabase() {
        _insects = dataSource.get()
    }
}