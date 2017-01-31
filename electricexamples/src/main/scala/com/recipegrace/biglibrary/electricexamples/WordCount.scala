package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.{SequenceFileJob, ElectricSession}

/**
  * Created by fjacob on 9/25/15.
  */

object WordCount extends SequenceFileJob[InputAndOutput] {


  override def execute(argument:InputAndOutput)(implicit ec: ElectricSession) = {
    val session = ec.getSparkSession

    import session.implicits._
    val file = ec.text(argument.input)
    val words = file.flatMap(_.toLowerCase.replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+"))
      words
        .groupByKey(f=> f)
        .count()
        .write
        .option("delimiter","\t")
        .csv(argument.output)

  }

}
