package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.tests.ElectricJobTest

/**
  * Created by fjacob on 9/25/15.
  */
class MultiplyListTest extends ElectricJobTest {

  test("multiply list") {


    val one = createFile {
      """ 1
        2 """
        .stripMargin
    }


    val output = createTempPath()
    launch(MultiplyList, MultiplyArgument(one, 4, output))

    val lines = readFilesInDirectory(output, "part")
    lines should contain("4")
    lines should contain("8")

  }

}
