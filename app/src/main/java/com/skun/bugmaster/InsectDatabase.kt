package com.skun.bugmaster

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.Executors


@Database(entities = [Insect::class], version = 1, exportSchema = false)
abstract class InsectDatabase : RoomDatabase() {
    abstract val insectDao: InsectDao

    companion object {
        val TAG: String? = this::class.simpleName
        val executorService = Executors.newSingleThreadScheduledExecutor()
        @Volatile
        private var INSTANCE: InsectDatabase? = null

        fun getInstance(context: Context): InsectDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        InsectDatabase::class.java,
                        "insect_database")
                        .fallbackToDestructiveMigration()
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                try {
                                    readInsectsFromResources(db, context)
                                } catch (e: IOException) {
                                    e.printStackTrace()
                                    Log.e(TAG, "Failed to read from file: " + e.message)
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                    Log.e(TAG, "Failed parsing JSON: " + e.message)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    Log.e(
                                        TAG,
                                        "Another error occurred while reading, parsing and inserting insect data to database: " + e.message
                                    )
                                }
                            }
                            override fun onOpen(db: SupportSQLiteDatabase) {
                                try {
                                    readInsectsFromResources(db, context)
                                } catch (e: IOException) {
                                    e.printStackTrace()
                                    Log.e(TAG, "Failed to read from file: " + e.message)
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                    Log.e(TAG, "Failed parsing JSON: " + e.message)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    Log.e(
                                        TAG,
                                        "Another error occurred while reading, parsing and inserting insect data to database: " + e.message
                                    )
                                }
                            }
                        })
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }

        /**
         * Streams the JSON data from insect.json, parses it, and inserts it into the
         * provided [SQLiteDatabase].
         *
         * @param db Database where objects should be inserted.
         * @throws IOException   Failed to read from file.
         * @throws JSONException Failed parsing JSON.
         */
        @Throws(IOException::class, JSONException::class)
        private fun readInsectsFromResources(db: SupportSQLiteDatabase, context: Context) {
            //Read file to a string
            val rawJson =  context.resources.openRawResource(R.raw.insects).bufferedReader().use{ it.readText() }

            //Parse resource into key/values
            val jsonObject = JSONObject(rawJson)
            val jsonArray = jsonObject.getJSONArray("insects")


            //Executors.newSingleThreadScheduledExecutor()
            executorService
                .execute(Runnable {
                    val dao = getInstance(context).insectDao

                    for (i in 0 until jsonArray.length()) {
                        /* Get the JSON object representing a insect */
                        val insectJsonObject = jsonArray.getJSONObject(i)

                        val insect = Insect()
                        insect.insectName = insectJsonObject.getString("friendlyName")
                        insect.insectScientificName = insectJsonObject.getString("scientificName")
                        insect.dangerLevel = insectJsonObject.getString("dangerLevel").toInt()
                        insect.classification = insectJsonObject.getString("classification")
                        insect.imageAsset =  insectJsonObject.getString("imageAsset")

                        if (dao.get(insect.insectName) === null)
                            dao.insert(insect)
                    }
                })
        }
    }
}