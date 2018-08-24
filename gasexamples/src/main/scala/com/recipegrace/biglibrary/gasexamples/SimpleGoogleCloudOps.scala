package com.recipegrace.biglibrary.gasexamples

import java.io.File
import java.nio.file.Files

import com.recipegrace.biglibrary.gas.gcloud.GoogleCloudStorage
import com.recipegrace.biglibrary.gas.{GCSInputLocation, GCSOutputLocation, GasJob, GasSession}

case class InAndOutput(input:GCSInputLocation, output:GCSOutputLocation)
object SimpleGoogleCloudOps extends GasJob[InAndOutput] {
  override def execute(t: InAndOutput)(implicit gs: GasSession): Unit = {
    System.out.println(scala.io.Source.fromFile(t.input.inputFile).size)
    upload("This is awesome".getBytes(),t.output)

  }
}
