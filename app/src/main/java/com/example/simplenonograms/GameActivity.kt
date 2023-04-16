package com.example.simplenonograms

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.simplenonograms.databinding.ActivityGameBinding



class GameActivity : AppCompatActivity() {

    var rowCount = 0
    var colCount = 0
    lateinit var array : Array<Array<Int>>
    lateinit var levelArray : Array<Array<Int>>
    lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createLevel()
        for (i in 0 until rowCount) {
            val tableRow = TableRow(this)
            tableRow.layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
            )

            for (j in 0 until colCount) {
                val cell = TextView(this)

                cell.text = ""

                cell.background = ResourcesCompat.getDrawable(resources,R.drawable.white_square,theme)
                cell.setOnClickListener {
                    array[i][j] = 1 - array[i][j]
                    if (array[i][j] == 1){
                        cell.background = ResourcesCompat.getDrawable(resources,R.drawable.blue_square, theme)}
                    else {cell.background = ResourcesCompat.getDrawable(resources,R.drawable.white_square,theme)}


                    if(array contentDeepEquals levelArray){
                        gameDoneAlert()
                    }
                }

                tableRow.addView(cell)
                val layoutParams = cell.layoutParams
                if (colCount>rowCount) {
                    layoutParams.height =
                        dpToPx(250 / colCount  )
                    layoutParams.width =
                        dpToPx(250 / colCount )
                }
                else{
                    layoutParams.height =
                        dpToPx(250 / rowCount )
                    layoutParams.width =
                        dpToPx(250 / rowCount )
                }
                cell.layoutParams = layoutParams


            }
            binding.gameBoard.addView(tableRow)

        }
        for (i in 0 until rowCount){
            val rowCounterCell = TextView(this)
            val trueCells = countTrueCellsInLine(levelArray[i])
            rowCounterCell.text = trueCells
            rowCounterCell.gravity = Gravity.END or Gravity.CENTER_VERTICAL
            rowCounterCell.background = ResourcesCompat.getDrawable(resources,R.drawable.white_square,theme)
            binding.rowCounterLine.addView(rowCounterCell)
            val layoutParams = rowCounterCell.layoutParams
            if (colCount>rowCount) {
                layoutParams.height =
                    dpToPx(250 / colCount  )
                layoutParams.width =
                    ViewGroup.LayoutParams.MATCH_PARENT
            }
            else{
                layoutParams.height =
                    dpToPx(250 / rowCount )
                layoutParams.width =
                    ViewGroup.LayoutParams.WRAP_CONTENT
            }
            rowCounterCell.layoutParams = layoutParams
            rowCounterCell.setPadding(10,10,10,10)
        }
        for (j in 0  until colCount){
            val colCounterCell = TextView(this)
            val verticalArray = Array<Int>(rowCount){0}
            for (i in 0 until rowCount){
                verticalArray[i] = levelArray[i][j]
            }
            val trueCells = countTrueCellsInLine(verticalArray)
            colCounterCell.text = trueCells
            colCounterCell.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            colCounterCell.background = ResourcesCompat.getDrawable(resources,R.drawable.white_square,theme)

            binding.columnCounterLine.addView(colCounterCell)
            val layoutParams = colCounterCell.layoutParams
            if (colCount>rowCount) {
                layoutParams.height =
                    ViewGroup.LayoutParams.WRAP_CONTENT

                layoutParams.width =
                    dpToPx(250 / colCount  )
            }
            else{
                layoutParams.height =
                    ViewGroup.LayoutParams.WRAP_CONTENT
                layoutParams.width =
                    dpToPx(250 / rowCount  )
            }
            colCounterCell.layoutParams = layoutParams
            colCounterCell.setPadding(10,10,10,10)
        }
    }

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }


    fun countTrueCellsInLine(arr:Array<Int>):String{
        var outputStr = ""
        var count = 0
        for (i in arr.indices) {
            Log.d("trueCellsInfoVertical","Элемент массива ${i}: ${arr[i]}")
            if (arr[i] == 1) {
                count++
            } else {
                if (count > 0) {
                    outputStr += "${count.toString()},"
                    count = 0
                }

            }
        }
        if(count != 0 ){
            outputStr += "${count.toString()},"
        }

        return outputStr.dropLast(1)
    }

    fun createLevel(){
        val isRandom = intent.getBooleanExtra("isRandom",false)
        if (isRandom){
            rowCount = intent.getIntExtra("rowsCount",5)
            colCount = intent.getIntExtra("colsCount",5)
            levelArray = Array(rowCount){Array<Int>(colCount){(0..1).random()}}
            array = Array(rowCount) { Array<Int>(colCount) {0} }
        }
        else{
            val levelName = intent.getStringExtra("levelName")
            val lineList = readLevelFile(this, levelName.toString())
            countRowsAndCols(lineList)
            rowCount = countRowsAndCols(lineList).first
            colCount = countRowsAndCols(lineList).second
            levelArray = Array(rowCount) { Array<Int>(colCount) {0} }
            array = Array(rowCount) { Array<Int>(colCount) {0} }
            for (i in 0 until lineList.size ){
                for (j in 0 until lineList[0].length){
                    levelArray[i][j] = lineList[i][j].toString().toInt()
                }
            }
        }
    }

    fun gameDoneAlert() {
        AlertDialog.Builder(this)
            .setTitle("Finished")
            .setMessage("Level Complete")
            .setPositiveButton(
                "Go Back"
            ) { _: DialogInterface, _: Int ->
                (this as AppCompatActivity).finish()
            }
            .setNegativeButton(
                "Reset"
            ) { _: DialogInterface, _: Int ->
                val mIntent = intent
                finish()
                startActivity(mIntent)

            }
            //.setIcon(android.R.drawable.star_big_on)
            .show()
    }

}