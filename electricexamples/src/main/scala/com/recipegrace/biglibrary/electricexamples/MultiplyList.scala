package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.jobs.ArgumentsToMap
import com.recipegrace.biglibrary.electric.{SequenceFileJob, ElectricSession}

/**
  * Created by Ferosh Jacob on 10/16/15.
  */

case class MultiplyArgument(input: String, factor: Int, output: String)

object MultiplyList extends SequenceFileJob[MultiplyArgument] with ArgumentsToMap {

  override def execute(t: MultiplyArgument)(implicit sc: ElectricSession): Unit = {
    val session = sc.getSparkSession

    import session.implicits._
      sc.text(t.input)
        .map(f => f.toInt * t.factor)
      .write
      .option("delimiter","\t")
      .csv(t.output)

  }
}
