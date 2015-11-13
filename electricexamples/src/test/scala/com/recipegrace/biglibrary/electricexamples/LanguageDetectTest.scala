package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.jobs.ThreeArgument
import com.recipegrace.biglibrary.electric.tests.{TwoInputJobTest, SimpleJobTest}

/**
 * Created by fjacob on 9/25/15.
 */
class LanguageDetectTest extends TwoInputJobTest with ParserContent{

  test("language detect test with spark") {



    val output = createTempPath()


    //launch(LanguageDetect, ThreeArgument("/Users/associate/searchterms/20151025_Internal_Search_DW_TW_20141001_20150930.txt","/Users/associate/tools/lang-detect/profiles.zip", output)
    launch(LanguageDetect, ThreeArgument("/Users/associate/searchterms/10000.txt","/Users/associate/tools/lang-detect/profiles.zip", output))
    //launch(LanguageDetect, ThreeArgument(input,"/Users/associate/tools/lang-detect/profiles.zip", output))

    val lines = readFilesInDirectory(output, "part")
    lines should contain("en\t1")
  }

}
