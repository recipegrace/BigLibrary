package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.jobs.ThreeArgument
import com.recipegrace.biglibrary.electric.tests.{TwoInputJobTest, SimpleJobTest}

/**
 * Created by fjacob on 9/25/15.
 */
class LanguageDetectTest extends TwoInputJobTest {

  test("wordcount test with spark") {


    val input = createFile {
      """
        hello world
        Zero world
        Some world
      """.stripMargin
    }

    val output = createTempPath()

    //launch(LanguageDetect, TwoArgument("/Users/associate/searchterms/20151025_Internal_Search_DW_TW_20141001_20150930.txt", output))
    launch(LanguageDetect, ThreeArgument(input,"/Users/associate/langdetect/profiles", output))

    val lines = readFilesInDirectory(output, "part")
    lines should contain("en\t1")
  }

}
