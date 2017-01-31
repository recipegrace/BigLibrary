package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.{SequenceFileJob, ElectricSession}

/**
  * Created by Ferosh Jacob on 10/16/15.
  */
case class OutputOnlyArgument(output:String)
object GenerateNumbers extends SequenceFileJob[OutputOnlyArgument] {

  override def execute(output:OutputOnlyArgument )(implicit ec: ElectricSession): Unit = {
   ec.getSparkSession.createDataFrame(Seq(
      (1,"a"),
      (2,"b")
    )).toDF("id","col")
       .select("id")
     .write.csv(output.output)

  }
}
