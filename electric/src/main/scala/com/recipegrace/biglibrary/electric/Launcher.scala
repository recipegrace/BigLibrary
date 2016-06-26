package com.recipegrace.biglibrary.electric

import java.nio.charset.Charset

import com.recipegrace.biglibrary.core.CreateTemporaryFiles

/**
  * Created by fjacob on 9/25/15.
  */
trait Launcher extends CreateTemporaryFiles {


  def launch[T](sparkJob: ElectricJob[T], args: T) = {
    sparkJob.runLocal(args)

  }


  def readSparkOut(dir: String,charset: Charset=Charset.forName("ISO-8859-1")) = {
    readFilesInDirectory(dir, "part",charset)
  }
}
