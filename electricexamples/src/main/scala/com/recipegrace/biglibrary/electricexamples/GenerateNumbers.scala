package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.ElectricContext
import com.recipegrace.biglibrary.electric.jobs.OutputOnlyJob

/**
 * Created by Ferosh Jacob on 10/16/15.
 */
object GenerateNumbers extends OutputOnlyJob{
  override def execute(output: String)(implicit ec: ElectricContext): Unit = {
    val list = ec.sparkContext.parallelize(List(1,2))
    list.foreach(println)

    writeFile(list, output)
  }
}
