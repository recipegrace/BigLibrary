package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.core.Mappable
import com.recipegrace.biglibrary.electric.{SequenceFileAccess, ElectricContext, ElectricJob}

/**
 * Created by Ferosh Jacob on 10/16/15.
 */

case class InputArgument(input:String, factor:Int, output:String)
object MultiplyList extends ElectricJob[InputArgument] with SequenceFileAccess {
  override implicit def argumentType: Mappable[InputArgument] = ???

  override def job(t: InputArgument)(implicit sc: ElectricContext): Unit = {
    val input=
      readFile(t.input)
      .map(f=> f.toInt * t.factor)

    writeFile(input,t.output)

  }
}
