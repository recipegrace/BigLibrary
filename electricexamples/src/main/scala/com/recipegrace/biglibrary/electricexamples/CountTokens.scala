package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.ElectricContext
import com.recipegrace.biglibrary.electric.jobs.{SimpleJob, TwoInputJob}
import org.apache.spark.SparkContext._

/**
 * Created by Ferosh Jacob on 10/30/15.
 */
object CountTokens extends SimpleJob with Parser{


  override def execute(input:String,output:String)(implicit ec: ElectricContext): Unit = {

     val content = inputParse(input)
       .map(f=> (f._1.split("\\s+",-1).size, f._2))
       .reduceByKey(_ + _)
       .map(f => f._1 + "\t" + f._2)

    writeFile(content, output)
  }
}
