package com.example.simplenonograms

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.simplenonograms.databinding.ActivityMainBinding
import com.example.simplenonograms.databinding.DialogueRandomLevelBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bindingMain: ActivityMainBinding
    private lateinit var bindingDialog:DialogueRandomLevelBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)

    }

    fun openLevelSelect(view: View){
        val intent = Intent(this, LevelSelectActivity::class.java)
        startActivity(intent)
    }

    fun openRandomLevelDialog(view: View){
        bindingDialog = DialogueRandomLevelBinding.inflate(layoutInflater).apply{

            colsPicker.maxValue = 8
            colsPicker.minValue = 3
            colsPicker.value = 5
            colsPicker.wrapSelectorWheel = false
            colsPicker.setOnValueChangedListener{_,_,_ ->
                rowsPicker.maxValue = colsPicker.value+2
            }

            rowsPicker.maxValue = colsPicker.value+2
            rowsPicker.minValue = 3
            rowsPicker.value = 5
            rowsPicker.wrapSelectorWheel = false


        }
        AlertDialog.Builder(this).setTitle(R.string.random_level_dialog_title).setMessage(R.string.random_level_dialog_message)
            .setView(bindingDialog.root)
            .setPositiveButton(R.string.random_level_dialog_create)
            {_,_->val intent = Intent(this,GameActivity::class.java)
            intent.putExtra("isRandom",true)
            intent.putExtra("rowsCount",bindingDialog.rowsPicker.value)
            intent.putExtra("colsCount",bindingDialog.colsPicker.value)
            startActivity(intent)}
            .setNegativeButton(R.string.random_level_dialog_cancel){_,_->}.create().show()
    }

}