package com.recipegrace.biglibrary.electric.spark

import org.apache.spark.sql.SparkSession

/**
  * Created by Ferosh Jacob on 1/20/16.
  */
trait SparkSessionCreator {

  def createLocalSparkSession(jobName: String) = {

    createSparkSession(true, jobName)
  }

  def createSparkSession(isLocal: Boolean, jobName: String) = {
    if( isLocal) SparkSession.builder()
          .appName(jobName)
          .master("local")
          .getOrCreate()
    else {
      SparkSession.builder().appName(jobName).getOrCreate()
    }


  }

  def createClusterSparkSession(jobName: String) = {

    createSparkSession(false, jobName)
  }
}
