package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.core.Mappable
import com.recipegrace.biglibrary.core.ParseArguments._
import com.recipegrace.biglibrary.electric.tests.ElectricJobTest


/**
 * Created by fjacob on 9/25/15.
 */
case class ExampleInput(var input: String, output: Int)

class SparkJobArgumentTest extends ElectricJobTest[ExampleInput] {


  test("two argument test") {


    val list = Array("--input", "bob", "--output", "22")
    val exampleInput = parse[ExampleInput](list).get
    exampleInput.input should be("bob")
    exampleInput.output should be(22)

  }







}

