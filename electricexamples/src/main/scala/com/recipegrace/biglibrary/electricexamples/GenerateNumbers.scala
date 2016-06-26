package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.{SequenceFileJob, ElectricContext}

/**
  * Created by Ferosh Jacob on 10/16/15.
  */
case class OutputOnlyArgument(output:String)
object GenerateNumbers extends SequenceFileJob[OutputOnlyArgument] {
  override def execute(output:OutputOnlyArgument )(implicit ec: ElectricContext): Unit = {
    val list = ec.sparkContext.parallelize(List(1, 2))
    list.foreach(println)

    writeFile(list, output.output)
  }
}
