package com.skun.bugmaster

import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.Image
import android.media.Rating
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import kotlinx.coroutines.ThreadContextElement
import org.w3c.dom.Text

class InsectDetailsActivity : AppCompatActivity() {
    private var insectEntity: Insect? = null
    private var rInsectAsset: ImageView? = null
    private var rName: TextView? = null
    private var rScientificName: TextView? = null
    private var rClassificationTitle: TextView? = null
    private  var rDangerLevel: RatingBar? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insect_details)

        rInsectAsset = findViewById<ImageView>(R.id.image_view) as ImageView
        rName = findViewById<TextView>(R.id.name) as TextView
        rScientificName = findViewById<TextView>(R.id.scientific_name) as TextView
        rClassificationTitle = findViewById<TextView>(R.id.classification_title) as TextView
        rDangerLevel = findViewById<RatingBar>(R.id.danger_level) as RatingBar

        if (intent != null) if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            val id = intent.getStringExtra(Intent.EXTRA_TEXT)?.toInt() ?: return

            LoadInsectTask().execute(id)
        }

    }

    inner class LoadInsectTask : AsyncTask<Int?, Void?, Insect?>() {

        override fun doInBackground(vararg id: Int?): Insect? {
            val dataSource = InsectDatabase.getInstance(application).insectDao
            return  dataSource.get(id[0] ?: return null)
        }

        override fun onPostExecute(insect: Insect?) {
            if(insect == null) return

            insectEntity = insect

            rName!!.text = insect.insectName
            rScientificName!!.text = insect.insectScientificName
            rClassificationTitle!!.text = "${getString(R.string.classification)} ${insect.classification}"
            rDangerLevel!!.setRating(insect.dangerLevel.toFloat())
            rInsectAsset!!.setImageDrawable(Drawable.createFromStream(getAssets().open(insect.imageAsset),null))
        }

    }
}