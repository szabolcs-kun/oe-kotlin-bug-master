package com.skun.bugmaster

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.skun.bugmaster.views.DangerLevelView

class InsectAdapter(
    private val insectList: List<InsectItem>,
    private val listener: OnItemClickListener
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
    }

    override fun getItemCount() = insectList.size
    inner class InsectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val customView: DangerLevelView = itemView.findViewById(R.id.dangerLevelView)
        val textView1: TextView = itemView.findViewById(R.id.name)
        val textView2: TextView = itemView.findViewById(R.id.scientific_name)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            /*
            val intent = Intent(v!!.context, DetailsActivity::class.java)
            intent.putExtra(
                Intent.EXTRA_TEXT,
                movie.id.toString()
            )
            ContextCompat.startActivity(view.context, intent, null)
            */


            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}