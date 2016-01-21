package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.ElectricContext
import com.recipegrace.biglibrary.electric.jobs.{ArgumentsToMap, SequenceFileJob}

/**
  * Created by Ferosh Jacob on 10/16/15.
  */

case class InputArgument(input: String, factor: Int, output: String)

object MultiplyList extends SequenceFileJob[InputArgument] with ArgumentsToMap {

  override def execute(t: InputArgument)(implicit sc: ElectricContext): Unit = {
    val input =
      readFile(t.input)
        .map(f => f.toInt * t.factor)

    writeFile(input, t.output)

  }

  override def parse(args: Array[String]): InputArgument = {
    require(args.length == 6, "Should have --input val --factor val --output val")

    val mapArgs = convertArgsToMap(args)

    require(mapArgs.contains("input"), "Should have --input val --factor val --output val")
    require(mapArgs.contains("factor"), "Should have --input val --factor val --output val")
    require(mapArgs.contains("output"), "Should have --input val --factor val --output val")

    InputArgument(mapArgs("input"), mapArgs("factor").toInt, mapArgs("output"))
  }
}
