package com.recipegrace.biglibrary.electricexamples

import java.nio.charset.Charset

import com.recipegrace.biglibrary.electric.jobs.ThreeArgument
import com.recipegrace.biglibrary.electric.tests.{TwoInputJobTest, SimpleJobTest}

/**
 * Created by fjacob on 9/25/15.
 */
class LanguageDetectTest extends TwoInputJobTest with ParserContent{

  test("language detect test with spark") {



    val output = createTempPath()


    launch(LanguageDetect, ThreeArgument("/Users/associate/searchterms/10000.txt","/Users/associate/tools/lang-detect/profiles.zip", output))

    val lines = readFilesInDirectory(output, "part", Charset.forName("ISO-8859-1"))
    lines should contain("pl\tzmax galvanized twist strap\t1")
  }

}
