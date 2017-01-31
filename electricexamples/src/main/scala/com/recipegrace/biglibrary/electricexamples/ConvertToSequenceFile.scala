package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.{ElectricSession, SequenceFileJob}

/**
  * Created by Ferosh Jacob on 11/3/15.
  */
case class InputAndOutput(input:String, output:String)
object ConvertToSequenceFile extends SequenceFileJob[InputAndOutput] {
  override def execute(argument:InputAndOutput)(implicit ec: ElectricSession): Unit = {

    val out = ec.text(argument.input)

    out.write.save(argument.output)
  }
}
