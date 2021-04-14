package com.skun.bugmaster

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast

class QuizActivity : AppCompatActivity() {

    private var insectsList: List<Insect>? = null

    private var rQuestionText: TextView? = null

    private var rRadioButtonList: List<RadioButton>? = null

    private var rCorrectText: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        rQuestionText = findViewById<TextView>(R.id.text_question) as TextView

        rRadioButtonList = listOf(
            findViewById<RadioButton>(R.id.radioButton1) as RadioButton,
            findViewById<RadioButton>(R.id.radioButton2) as RadioButton,
            findViewById<RadioButton>(R.id.radioButton3) as RadioButton,
            findViewById<RadioButton>(R.id.radioButton4) as RadioButton
        )

        rCorrectText = findViewById<TextView>(R.id.text_correct) as TextView

        LoadQuizTask().execute(0)
    }


    inner class LoadQuizTask : AsyncTask<Int?, Void?, List<Insect>?>() {

        override fun doInBackground(vararg id: Int?): List<Insect>? {
            val dataSource = InsectDatabase.getInstance(application).insectDao
            return  dataSource.getRandomEntries(4 ?: return null)
        }

        override fun onPostExecute(insects: List<Insect>?) {
            if(insects == null) return

            insectsList = insects

            rQuestionText!!.text =  "${getString(R.string.question)}${insectsList!![0].insectName}?"

            var randomizedInsectsList = insectsList!!.shuffled()

            var idx: Int = 0;
            rRadioButtonList!!.forEach {

                it.text = randomizedInsectsList[idx].insectScientificName

                if (randomizedInsectsList[idx].insectScientificName == insectsList!![0].insectScientificName)
                {
                    it.setOnClickListener {
                        correctAnswer()
                    }
                }
                else{
                    it.setOnClickListener {
                        wrongAnswer()
                    }
                }

                idx++;
            }
        }

    }

    fun correctAnswer()
    {
        rCorrectText!!.setTextColor(Color.GREEN)
        rCorrectText!!.text = getString(R.string.correct)
    }

    fun wrongAnswer()
    {
        rCorrectText!!.setTextColor(Color.RED)
        rCorrectText!!.text = getString(R.string.wrong)
    }
}