package com.recipegrace.biglibrary.gasexamples

import java.io.File
import java.nio.file.Files

import com.recipegrace.biglibrary.gas.gcloud.GoogleCloudStorage
import com.recipegrace.biglibrary.gas.{GCSInputLocation, GCSOutputLocation, GasJob, GasSession}

object GoogleCloudDirectoryAccess extends GasJob[InAndOutput] {
  override def execute(t: InAndOutput)(implicit gs: GasSession): Unit = {
    val directory = new File(t.input.inputFile)
    val output = directory.listFiles.toList.flatMap(f=> scala.io.Source.fromFile(f).getLines).mkString(",")
    upload(output.getBytes,t.output)
  }
}
