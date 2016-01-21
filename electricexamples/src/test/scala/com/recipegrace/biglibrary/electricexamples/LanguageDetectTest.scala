package com.recipegrace.biglibrary.electricexamples

import java.nio.charset.Charset

import com.recipegrace.biglibrary.electric.jobs.Arguments.ThreeArgument
import com.recipegrace.biglibrary.electric.tests.TwoInputJobTest

/**
  * Created by fjacob on 9/25/15.
  */
class LanguageDetectTest extends TwoInputJobTest  {

  test("language detect test with spark") {


    val output = createTempPath()
    val content = createFile(
      """ Hello world is a very popular word
        Hello again """.stripMargin)

    launch(LanguageDetect, ThreeArgument(content, "files/profiles.zip", output))

    val lines = readFilesInDirectory(output, "part", Charset.forName("ISO-8859-1"))
    lines should contain("en\tHello world is a very popular word")
  }

}
