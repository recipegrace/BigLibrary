package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.tests.{SimpleJobTest, ElectricJobTest}
import com.recipegrace.biglibrary.electric.jobs.TwoArgument

/**
 * Created by fjacob on 9/25/15.
 */
class WordCountTest extends SimpleJobTest{

  test("wordcount test with spark") {


    val input= createFile{
      """
        hello world
        Zero world
        Some world
      """.stripMargin
    }

    val output= createTempPath()

    launch(WordCount, TwoArgument(input,output))

    val lines = readFilesInDirectory(output, "part")
    lines should contain("hello\t1")
    lines should contain("world\t3")
  }

}
