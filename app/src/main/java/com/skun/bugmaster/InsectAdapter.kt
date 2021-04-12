package com.skun.bugmaster

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.skun.bugmaster.views.DangerLevelView

class InsectAdapter(
    private val insectList: List<Insect>
) :
    RecyclerView.Adapter<InsectAdapter.InsectViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InsectViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.insect_item,
            parent, false
        )

        return InsectViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InsectViewHolder, position: Int) {
        val currentItem = insectList[position]

        holder.customView.setDangerLevel(currentItem.dangerLevel)
        holder.textView1.text = currentItem.insectName
        holder.textView2.text = currentItem.insectScientificName

        holder.itemView.setOnClickListener { view ->
            val intent = Intent(view.context, InsectDetailsActivity::class.java)
            intent.putExtra(
                Intent.EXTRA_TEXT,
                currentItem.id.toString()
            )
            ContextCompat.startActivity(view.context, intent, null)
        }
    }

    override fun getItemCount() = insectList.size

    class InsectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val customView: DangerLevelView = itemView.findViewById(R.id.dangerLevelView)
        val textView1: TextView = itemView.findViewById(R.id.name)
        val textView2: TextView = itemView.findViewById(R.id.scientific_name)

    }
}