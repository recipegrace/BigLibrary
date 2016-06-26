package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.{SequenceFileJob, ElectricContext}


/**
  * Created by Ferosh Jacob on 10/16/15.
  */
case class InputsAndOutput(input1:String,input2:String, output:String)
object AppendList extends SequenceFileJob[InputsAndOutput] {
  override def execute(arg:InputsAndOutput)(implicit ec: ElectricContext): Unit = {


    val first = readFile(arg.input1)
    val second = readFile(arg.input2)
    writeFile(first ++ second, arg.output)
  }
}
