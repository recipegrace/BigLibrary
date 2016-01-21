package com.recipegrace.biglibrary.electric.tests

import com.recipegrace.biglibrary.core.BaseTest
import com.recipegrace.biglibrary.electric.ElectricContext
import com.recipegrace.biglibrary.electric.spark.SparkContextCreator
import org.apache.spark.SparkContext


/**
  * Created by Ferosh Jacob on 1/20/16.
  */


abstract class ElectricTraitTest extends BaseTest with SparkContextCreator {


  def loadContext={
    val sc: SparkContext = createSparkContext(true, System.currentTimeMillis() + "")
    ElectricContext(true, sc)
  }
}