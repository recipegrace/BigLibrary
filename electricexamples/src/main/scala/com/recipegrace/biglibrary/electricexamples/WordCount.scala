package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.core.Argument
import com.recipegrace.biglibrary.electric.{ElectricContext, SequentialFileAccess, ElectricJob}


/**
 * Created by fjacob on 9/25/15.
 */

object WordCount extends ElectricJob with SequentialFileAccess {

  val inputArgument = Argument("input")
  val outputArgument = Argument("output")

  override  val namedArguments = Set(inputArgument, outputArgument)

  override def job(args: Map[Argument, String], sc: ElectricContext) = {
    implicit val context=sc
    val file = readFile(args(inputArgument))
    val words = file.flatMap(_.toLowerCase.replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+"))
    val wordCounts = words
      .map(x => (x, 1)).reduceByKey(_ + _)
      .map(f => f._1 + "\t" + f._2)

    writeFile(wordCounts,args(outputArgument))

  }

}
