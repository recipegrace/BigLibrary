package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.core.{Argument, ParseArguments}
import com.recipegrace.biglibrary.electric.{ElectricJobTest, ElectricContext, ElectricJob}
/**
 * Created by fjacob on 9/25/15.
 */
class SparkJobArgumentTest extends ElectricJobTest with ParseArguments {



  test("two argument test") {

    val arguments = Array("--input", "inputValue", "--output", "outputValue")

    val result = parse(arguments, Set(Argument("input"), Argument("output", true, "hello")))
    result should have size (2)
    result.toList.map(_._2) should contain ("inputValue")
    result.toList.map(_._2) should contain ("outputValue")
  }



  test("single argument test") {

    val arguments = Array("-i","input", "-o", "output")


    val result = parse(arguments, Set(Argument("input1"), Argument("output", true, "hello")))
    result should have size (2)
    result.toList.map(_._2) should contain ("output")

  }



}

