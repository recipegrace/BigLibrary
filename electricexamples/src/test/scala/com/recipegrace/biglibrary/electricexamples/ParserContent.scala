package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.core.CreateTemporaryFiles

/**
  * Created by Ferosh Jacob on 11/3/15.
  */
trait ParserContent extends CreateTemporaryFiles {


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

}
