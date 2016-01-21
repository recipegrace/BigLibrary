package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.core.CreateTemporaryFiles
import com.recipegrace.biglibrary.electric.tests.ElectricTraitTest

/**
  * Created by Ferosh Jacob on 1/20/16.
  */
class ParserTest extends ElectricTraitTest with Parser with CreateTemporaryFiles {

  test("parser test") {


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
    implicit val ec = loadContext
    readFile(input).collect() should have size(8)
  }

}
