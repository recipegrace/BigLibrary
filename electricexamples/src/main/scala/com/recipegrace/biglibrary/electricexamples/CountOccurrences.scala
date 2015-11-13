package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.core.StringCompare
import com.recipegrace.biglibrary.electric.ElectricContext
import com.recipegrace.biglibrary.electric.jobs.TwoInputJob
import org.apache.spark.SparkFiles

import org.apache.spark.SparkContext._

/**
 * Created by Ferosh Jacob on 11/3/15.
 */
object CountOccurrences extends TwoInputJob with StringCompare with Parser{



  override def execute(one: String, two: String, output: String)(implicit ec: ElectricContext): Unit = {

    ec.sparkContext.addFile(two)
    val selectedContent = scala.io.Source.fromFile(SparkFiles.get("ProductTypes-1.txt")).getLines().toList

    val total = inputParse(one)
      .reduceByKey(_ + _)
      .map(f=> (0, f._2))
      .reduceByKey(_ + _)

    val content = inputParse(one)

    .map( f=> {
      val text = f._1
      val products=  selectedContent.map(f=> (onewayCompare(text,f),f)).filter(f=> f._1 > 0.95).map(f=>f._2)
      (text,products,f._2)
    })
    .filter(f=> f._2.size >0)
    .map(f=> (f._2.size, f._3))
      .reduceByKey(_ + _)


  val finalOut = (total++content)
    .map(f=> f._1 +"\t" + f._2)
    writeFile(finalOut, output)
  }


}
