package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.ElectricContext
import com.recipegrace.biglibrary.electric.jobs.SimpleJob

/**
 * Created by Ferosh Jacob on 11/3/15.
 */
object ConvertToSequenceFile extends SimpleJob {
  override def execute(input: String, output: String)(implicit ec: ElectricContext): Unit = {

    val out =   ec.sparkContext.textFile(input)

    writeFile(out,output)
  }
}
