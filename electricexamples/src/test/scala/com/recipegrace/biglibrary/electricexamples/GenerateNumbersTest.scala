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
  test("generate number") {

    val input = createFile {
      """
        hello world
        Zero world
        Some world
      """.stripMargin
    }

    val output = createTempPath()

    WordCount.runLocal(TwoArgument(input, output))

    val lines = readFilesInDirectory(output, "part")
    lines should contain("hello\t1")
    lines should contain("world\t3")
  }

}
