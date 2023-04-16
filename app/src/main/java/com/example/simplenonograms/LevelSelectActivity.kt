package com.example.simplenonograms

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simplenonograms.databinding.ActivityLevelSelectBinding

class LevelSelectActivity : AppCompatActivity() , LevelSelectAdapter.StartLevel {
    lateinit var binding: ActivityLevelSelectBinding
    private lateinit var adapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLevelSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = LevelSelectAdapter(getAllLevels(this),this,this)

        binding.apply {
            rvLevels.layoutManager = GridLayoutManager (this@LevelSelectActivity, 3)
            rvLevels.adapter = adapter
        }
    }


    fun getAllLevels(context: Context): List<String>{
        val files = context.resources.assets.list("levels/")?.toList()
        return files ?: listOf()
    }

    override fun startLevel(levelName: String){
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("levelName", levelName)
        startActivity(intent)
    }

}