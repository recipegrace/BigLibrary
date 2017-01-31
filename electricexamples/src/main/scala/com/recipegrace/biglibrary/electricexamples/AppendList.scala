package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.{SequenceFileJob, ElectricSession}


/**
  * Created by Ferosh Jacob on 10/16/15.
  */
case class InputsAndOutput(input1:String,input2:String, output:String)
object AppendList extends SequenceFileJob[InputsAndOutput] {
  override def execute(arg:InputsAndOutput)(implicit ec: ElectricSession): Unit = {



    val first = ec.text(arg.input1)
    val second = ec.text(arg.input2)


    first.union(second).write.csv(arg.output)
  }
}
