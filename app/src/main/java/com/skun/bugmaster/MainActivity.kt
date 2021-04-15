package com.skun.bugmaster

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var gAdapter: InsectAdapter? = null
    private var gData: List<Insect>? = null
    private var orderTrackerDangerLevel: Boolean = false
    private var orderTrackerInsectName: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))


        LoadInsectsTask().execute()


        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val intent = Intent(view.context, QuizActivity::class.java)
            ContextCompat.startActivity(view.context, intent, null)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        menu.findItem(R.id.action_sort_danger).title = "${getString(R.string.action_sort_danger)} ASC"
        menu.findItem(R.id.action_sort_insect).title = "${getString(R.string.action_sort_insect)} ASC"
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_sort_danger ->
                if (orderTrackerDangerLevel)
                {
                    orderTrackerDangerLevel = false
                    item.title = "${getString(R.string.action_sort_danger)} ASC"
                    orderByDangerLevelDesc()
                }
            else
                {
                    orderTrackerDangerLevel = true
                    item.title = "${getString(R.string.action_sort_danger)} DESC"
                    orderByDangerLevelAsc()
                }
            R.id.action_sort_insect ->
                if (orderTrackerInsectName)
                {
                    orderTrackerInsectName = false
                    item.title = "${getString(R.string.action_sort_insect)} ASC"
                    orderByInsectNameDesc()
                }
                else
                {
                    orderTrackerInsectName = true
                    item.title = "${getString(R.string.action_sort_insect)} DESC"
                    orderByInsectNameAsc()
                }
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun orderByDangerLevelAsc(): Boolean
    {
        val dataSource = InsectDatabase.getInstance(application).insectDao
        val tmp = gAdapter?.insectList?.sortedWith(Comparator<Insect>{ a, b ->
            when {
                a.dangerLevel > b.dangerLevel -> 1
                a.dangerLevel < b.dangerLevel -> -1
                else -> 0
            }
        })

        if (tmp != null)
        {
            gAdapter?.insectList = tmp
            //recyclerView?.adapter = gAdapter
            gAdapter?.notifyDataSetChanged()
        }


        Toast.makeText(this,
            "Ordered by danger level ASC", Toast.LENGTH_LONG).show();
        return true
    }

    fun orderByDangerLevelDesc(): Boolean
    {
        val dataSource = InsectDatabase.getInstance(application).insectDao
        val tmp = gAdapter?.insectList?.sortedWith(Comparator<Insect>{ a, b ->
            when {
                a.dangerLevel < b.dangerLevel -> 1
                a.dangerLevel > b.dangerLevel -> -1
                else -> 0
            }
        })

        if (tmp != null)
        {
            gAdapter?.insectList = tmp
            //recyclerView?.adapter = gAdapter
            gAdapter?.notifyDataSetChanged()
        }


        Toast.makeText(this,
            "Ordered by danger level DESC", Toast.LENGTH_LONG).show();
        return true
    }

    fun orderByInsectNameAsc(): Boolean
    {
        val dataSource = InsectDatabase.getInstance(application).insectDao
        val tmp = gAdapter?.insectList?.sortedWith(Comparator<Insect>{ a, b ->
            when {
                a.insectName > b.insectName -> 1
                a.insectName < b.insectName -> -1
                else -> 0
            }
        })

        if (tmp != null)
        {
            gAdapter?.insectList = tmp
            //recyclerView?.adapter = gAdapter
            gAdapter?.notifyDataSetChanged()
        }


        Toast.makeText(this,
            "Ordered by insect name ASC", Toast.LENGTH_LONG).show();
        return true
    }

    fun orderByInsectNameDesc(): Boolean
    {
        val dataSource = InsectDatabase.getInstance(application).insectDao
        val tmp = gAdapter?.insectList?.sortedWith(Comparator<Insect>{ a, b ->
            when {
                a.insectName < b.insectName -> 1
                a.insectName > b.insectName -> -1
                else -> 0
            }
        })

        if (tmp != null)
        {
            gAdapter?.insectList = tmp
            //recyclerView?.adapter = gAdapter
            gAdapter?.notifyDataSetChanged()
        }


        Toast.makeText(this,
            "Ordered by insect name DESC", Toast.LENGTH_LONG).show();
        return true
    }

    inner class LoadInsectsTask : AsyncTask<Int?, Void?, List<Insect>?>() {

        override fun doInBackground(vararg id: Int?): List<Insect>? {
            val dataSource = InsectDatabase.getInstance(application).insectDao

            var data = dataSource.get()

            while (data.isEmpty()) {
                Thread.sleep(100L)
                data = dataSource.get()
            }

            return data
        }

        //@Override
        override fun onPostExecute(insects: List<Insect>?) {
            if (insects == null) return
            gData = insects
            gAdapter = InsectAdapter(gData!!)

            recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
            recyclerView!!.adapter = gAdapter
            recyclerView!!.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerView!!.setHasFixedSize(true)
        }
    }
}