package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.ElectricContext
import com.recipegrace.biglibrary.electric.jobs.SimpleJob
import com.recipegrace.biglibrary.electric.tests.SimpleJobTest


/**
 * Created by fjacob on 9/25/15.
 */

class SparkJobArgumentTest extends SimpleJobTest{


  test("two argument test") {


    val list = Array("--input", "bob", "--output", "22")
    val exampleInput= new SimpleJob {
      override def execute(input: String, output: String)(ec: ElectricContext): Unit = ???
    }parse(list)
    exampleInput.input should be("bob")
    exampleInput.output should be("22")

  }







}

