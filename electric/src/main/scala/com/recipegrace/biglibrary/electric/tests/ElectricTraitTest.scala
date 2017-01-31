package com.recipegrace.biglibrary.electric.tests

import com.recipegrace.biglibrary.core.{BaseTest, CreateTemporaryFiles}
import com.recipegrace.biglibrary.electric.ElectricSession
import com.recipegrace.biglibrary.electric.spark.SparkSessionCreator
import org.apache.spark.SparkContext


/**
  * Created by Ferosh Jacob on 1/20/16.
  */


abstract class ElectricTraitTest extends BaseTest with SparkSessionCreator  with CreateTemporaryFiles{


  def loadContext = {
    val ss = createSparkSession(true, System.currentTimeMillis() + "")
    new ElectricSession(true, ss)
  }
}