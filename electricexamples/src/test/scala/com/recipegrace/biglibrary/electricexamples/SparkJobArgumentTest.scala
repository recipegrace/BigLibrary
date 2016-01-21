package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.ElectricContext
import com.recipegrace.biglibrary.electric.jobs.{OutputOnlyJob, TwoInputJob, SimpleJob}
import com.recipegrace.biglibrary.electric.tests.SimpleJobTest


/**
  * Created by fjacob on 9/25/15.
  */

class SparkJobArgumentTest extends SimpleJobTest {


  test("two argument test") {


    val list = Array("--input", "bob", "--output", "22")
    val exampleInput = new SimpleJob {
      override def execute(input: String, output: String)(implicit ec: ElectricContext): Unit = ???
    } parse (list)
    exampleInput.input should be("bob")
    exampleInput.output should be("22")

  }

  test("three argument test") {


    val list = Array("--one", "bob","--two","sally", "--output", "22")
    val exampleInput = new TwoInputJob {
      override def execute(one: String, two:String, output: String)(implicit ec: ElectricContext): Unit = ???
    } parse (list)
    exampleInput.one should be("bob")
    exampleInput.output should be("22")
    exampleInput.two should be("sally")

  }
  test("one argument test") {


    val list = Array("--output", "22")
    val exampleInput = new OutputOnlyJob {
      override def execute(output: String)(implicit ec: ElectricContext): Unit = ???
    } parse (list)
    exampleInput.output should be("22")

  }

}

