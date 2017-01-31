package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.jobs.ArgumentsToMap
import com.recipegrace.biglibrary.electric.{SequenceFileJob, ElectricContext}

/**
  * Created by Ferosh Jacob on 10/16/15.
  */

case class MultiplyArgument(input: String, factor: Int, output: String)

object MultiplyList extends SequenceFileJob[MultiplyArgument] with ArgumentsToMap {

  override def execute(t: MultiplyArgument)(implicit sc: ElectricContext): Unit = {
    val input =
      readFile(t.input)
        .map(f => f.toInt * t.factor)

    writeFile(input, t.output)

  }
}
