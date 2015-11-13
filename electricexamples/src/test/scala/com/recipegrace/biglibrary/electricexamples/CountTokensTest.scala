package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.jobs.TwoArgument
import com.recipegrace.biglibrary.electric.tests.SimpleJobTest

/**
 * Created by fjacob on 9/25/15.
 */
class CountTokensTest extends SimpleJobTest with ParserContent {

  test("count tokens test with spark") {


    val output = createTempPath()

    launch(CountTokens, TwoArgument(input, output))

    val lines = readFilesInDirectory(output, "part")
    lines should contain("1\t2305326")
  }

}
