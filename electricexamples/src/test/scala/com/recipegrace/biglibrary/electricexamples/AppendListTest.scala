package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.tests.ElectricJobTest


/**
  * Created by fjacob on 9/25/15.
  */
class AppendListTest extends ElectricJobTest {

  test("append list test") {


    val one = createFile {
      """ 1
        2 """
        .stripMargin
    }


    val two = createFile {
      """ a
        b """.stripMargin
    }

    val output = createTempPath()
    launch(AppendList, InputsAndOutput(one, two, output))

    val lines = readFilesInDirectory(output, "part")
    lines should contain("1")
    lines should contain("a")

  }

}
