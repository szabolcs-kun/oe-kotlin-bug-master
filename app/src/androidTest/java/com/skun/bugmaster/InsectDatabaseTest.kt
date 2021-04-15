package com.skun.bugmaster

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class InsectDatabaseTest {
    private lateinit var insectDao: InsectDao
    private lateinit var db: InsectDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(context, InsectDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        insectDao = db.insectDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetInsect() {
        val insect = Insect(id = 1)
        insectDao.insert(insect)
        val dbInsect = insectDao.get(1)
        assertEquals(insect.insectName, dbInsect?.insectName)
    }
}