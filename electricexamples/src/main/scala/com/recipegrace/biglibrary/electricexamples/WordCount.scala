package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.ElectricContext
import com.recipegrace.biglibrary.electric.jobs.SimpleJob

/**
  * Created by fjacob on 9/25/15.
  */

object WordCount extends SimpleJob {


  override def execute(input: String, output: String)(implicit ec: ElectricContext) = {
    val file = readFile(input, false)
    val words = file.flatMap(_.toLowerCase.replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+"))
    val wordCounts = words
      .map(x => (x, 1)).reduceByKey(_ + _)
      .map(f => f._1 + "\t" + f._2)
    writeFile(wordCounts, output)


  }

}
