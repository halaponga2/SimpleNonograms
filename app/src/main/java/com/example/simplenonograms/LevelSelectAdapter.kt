package com.example.simplenonograms

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simplenonograms.databinding.LevelViewBinding


class LevelSelectAdapter(private val levels: List<String>, private val startLevel: StartLevel, var context: Context): RecyclerView.Adapter<LevelSelectAdapter.LevelViewHolder>() {
    class LevelViewHolder(levelView: View): RecyclerView.ViewHolder(levelView){
        val binding = LevelViewBinding.bind(levelView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        val levelView = LayoutInflater.from(parent.context).inflate(R.layout.level_view, parent, false) as View
        return LevelViewHolder(levelView)
    }

    override fun getItemCount(): Int {
        return levels.size
    }
    interface StartLevel{
        fun startLevel(levelName: String)
    }

    override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {
        val levelRowsAndCols = countRowsAndCols(readLevelFile(context,levels[position]))
        holder.binding.levelName.text = levels[position]
        holder.binding.levelSize.text = context.getString(R.string.rows_x_cols,levelRowsAndCols.first,levelRowsAndCols.second)
        holder.binding.bStartLevel.setOnClickListener{
            startLevel.startLevel(levels[position])
        }
    }



}