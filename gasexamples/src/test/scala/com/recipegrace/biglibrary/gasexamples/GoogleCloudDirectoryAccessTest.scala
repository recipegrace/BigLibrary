package com.recipegrace.biglibrary.gasexamples

import com.recipegrace.biglibrary.gas.{GCSInputLocation, GCSOutputLocation}
import com.recipegrace.biglibrary.gas.tests.GasJobTest

class GoogleCloudDirectoryAccessTest extends GasJobTest {


  test("google cloud directory") {
    val output = createTempPath()
    launch(GoogleCloudDirectoryAccess,InAndOutput(GCSInputLocation ("files/terms"), GCSOutputLocation( output )))
    scala.io.Source.fromFile(output).getLines().toList.head shouldBe "one,two,three"
  }
}
