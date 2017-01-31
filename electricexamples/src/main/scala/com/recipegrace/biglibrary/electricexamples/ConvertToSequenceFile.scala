package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.{SequenceFileJob, ElectricContext}

/**
  * Created by Ferosh Jacob on 11/3/15.
  */
case class InputAndOutput(input:String, output:String)
object ConvertToSequenceFile extends SequenceFileJob[InputAndOutput] {
  override def execute(argument:InputAndOutput)(implicit ec: ElectricContext): Unit = {

    val out = ec.sparkContext.textFile(argument.input)

    writeFile(out, argument.output)
  }
}
