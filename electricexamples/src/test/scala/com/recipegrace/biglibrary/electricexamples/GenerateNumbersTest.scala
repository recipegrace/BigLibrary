package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.tests.{OutputOnlyJobTest, SimpleJobTest}

import com.recipegrace.biglibrary.electric.jobs._

/**
 * Created by fjacob on 9/25/15.
 */
class GenerateNumbersTest extends OutputOnlyJobTest{

  test("generate number") {




    val output= createTempPath()

    launch(GenerateNumbers, OneArgument(output))

    val lines = readFilesInDirectory(output, "part")
    lines should contain("1")
  }

}
