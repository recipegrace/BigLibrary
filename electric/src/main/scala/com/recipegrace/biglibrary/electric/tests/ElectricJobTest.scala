package com.recipegrace.biglibrary.electric.tests

import com.recipegrace.biglibrary.core.BaseTest
import com.recipegrace.biglibrary.core.ParameterizedJobTest
import java.nio.charset.Charset
/**
  * Created by fjacob on 6/1/15.
  */

trait ElectricJobTest extends ParameterizedJobTest {

  def readSparkOut(dir: String,charset: Charset=Charset.forName("ISO-8859-1")) = {
    readFilesInDirectory(dir, "part",charset)
  }

}




