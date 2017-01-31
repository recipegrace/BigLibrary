package com.recipegrace.biglibrary.electric

import org.apache.spark.sql.{Dataset, Encoder, SparkSession}
/**
  * Created by Ferosh Jacob on 1/29/17.
  */
class ElectricSession(isLocal: Boolean, sparkSession: SparkSession) {

  def text(fileName:String):Dataset[String] = {

    import sparkSession.implicits._
    sparkSession.read.text(fileName).map(f=> f.getAs[String](0))
  }

  def tabFile(fileName:String, header:Boolean=false) = {
    if(header)
    sparkSession
      .read
      .option("header", true)
      .option("delimiter", "\t").text(fileName)

  }
  def getSparkSession=this.sparkSession



  def addFile(fileName:String) = {
    sparkSession.sparkContext.addFile(fileName)
  }
}
