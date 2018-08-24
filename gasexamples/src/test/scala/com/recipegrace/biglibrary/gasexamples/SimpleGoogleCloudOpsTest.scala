package com.recipegrace.biglibrary.gasexamples

import com.recipegrace.biglibrary.gas.{GCSInputLocation, GCSOutputLocation}
import com.recipegrace.biglibrary.gas.tests.GasJobTest

class SimpleGoogleCloudOpsTest extends GasJobTest {


  test("simple ops") {
    val output = createTempPath()
    launch(SimpleGoogleCloudOps,InAndOutput(GCSInputLocation ("files/terms.txt"), GCSOutputLocation( output )))

    scala.io.Source.fromFile(output).getLines().toList.head shouldBe "This is awesome"
  }
}
