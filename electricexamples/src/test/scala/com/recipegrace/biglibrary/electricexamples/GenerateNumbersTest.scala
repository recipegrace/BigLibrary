package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.jobs._
import com.recipegrace.biglibrary.electric.tests.OutputOnlyJobTest

/**
 * Created by fjacob on 9/25/15.
 */
class GenerateNumbersTest extends OutputOnlyJobTest {

  test("generate number") {


    val output = createTempPath()

    launch(GenerateNumbers, OneArgument(output))

    val lines = readFilesInDirectory(output, "part")
    lines should contain("1")
  }

}
