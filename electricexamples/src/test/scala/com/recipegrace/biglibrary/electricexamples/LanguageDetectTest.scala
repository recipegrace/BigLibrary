package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.jobs.ThreeArgument
import com.recipegrace.biglibrary.electric.tests.{TwoInputJobTest, SimpleJobTest}

/**
 * Created by fjacob on 9/25/15.
 */
class LanguageDetectTest extends TwoInputJobTest {

  test("language detect test with spark") {


    val input = createFile {
      """
        33952452	toilet	1177157	1092
        41634937	bathroom vanity	1141770	4
        34459005	refrigerator	1128169	0
        30795278	pressure washer	1124851	138
        13065904	water heater	1104370	0
        452667	snow blower	1056742	0
      """.stripMargin
    }

    val output = createTempPath()


    //launch(LanguageDetect, ThreeArgument("/Users/associate/searchterms/20151025_Internal_Search_DW_TW_20141001_20150930.txt","/Users/associate/tools/lang-detect/profiles.zip", output)
    launch(LanguageDetect, ThreeArgument("/Users/associate/searchterms/10000.txt","/Users/associate/tools/lang-detect/profiles.zip", output))
    //launch(LanguageDetect, ThreeArgument(input,"/Users/associate/tools/lang-detect/profiles.zip", output))

    val lines = readFilesInDirectory(output, "part")
    lines should contain("en\t1")
  }

}
