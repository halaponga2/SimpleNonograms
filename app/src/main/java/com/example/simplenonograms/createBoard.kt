package com.example.simplenonograms

import android.content.Context
import java.io.InputStream

fun countRowsAndCols(levelArray:MutableList<String>):Pair<Int,Int> {
    val rowCount = levelArray.size
    val colCount = levelArray[0].length
    return Pair(rowCount, colCount)
}

fun readLevelFile(context: Context, levelName: String):MutableList<String>{
    val inputStream: InputStream = context.resources.assets.open("levels/$levelName")
    val lineList = mutableListOf<String>()
    inputStream.bufferedReader().forEachLine { lineList.add(it)}
    return lineList
}