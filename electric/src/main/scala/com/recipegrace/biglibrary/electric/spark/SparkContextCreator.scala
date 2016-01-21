package com.recipegrace.biglibrary.electric.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Ferosh Jacob on 1/20/16.
  */
trait SparkContextCreator {

  def createLocalSparkContext(jobName: String) = {

    createSparkContext(true, jobName)
  }

  def createSparkContext(isLocal: Boolean, jobName: String) = {
    val jars = if (isLocal) List() else List(SparkContext.jarOfObject(this).get)

    val sc: SparkContext = {
      val conf = new SparkConf()
        .setAppName(jobName)
        .setJars(jars)
        .set("spark.driver.allowMultipleContexts", "true")
      if (isLocal)
        conf.setMaster("local")
      new SparkContext(conf)
    }
    sc
  }

  def createClusterSparkContext(jobName: String) = {

    createSparkContext(false, jobName)
  }
}
