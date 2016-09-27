package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.tests.ElectricJobTest


/**
  * Created by fjacob on 9/25/15.
  */
class GenerateNumbersTest extends ElectricJobTest {

  test("generate number") {


    val output = createTempPath()

    launch(GenerateNumbers, OutputOnlyArgument(output))

    val lines = readFilesInDirectory(output, "part")
    lines should contain("1")
  }


}
