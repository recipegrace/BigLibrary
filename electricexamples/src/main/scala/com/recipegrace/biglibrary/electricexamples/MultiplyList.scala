package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.ElectricContext
import com.recipegrace.biglibrary.electric.jobs.{ArgumentsToMap, SequenceFileJob}

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

  override def parse(args: Array[String]): MultiplyArgument = {


    val input="input"
    val factor="factor"
    val output ="output"

    val mainText = s"Should have --$input val --$factor val --$output"
    val mapArgs = convertArgsToMap(args)
    validateArgs(args, mapArgs, mainText, input, factor, output)

    MultiplyArgument(mapArgs(input),  mapArgs(output).toInt, mapArgs(factor))
  }
}
