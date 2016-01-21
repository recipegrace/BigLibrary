package com.recipegrace.biglibrary.electric

import com.recipegrace.biglibrary.core.CreateTemporaryFiles

/**
  * Created by fjacob on 9/25/15.
  */
trait Launcher[T] extends CreateTemporaryFiles {


  def launch(sparkJob: ElectricJob[T], args: T) = {
    sparkJob.runLocal(args)
  }


  def readSparkOut(dir: String) = {
    readFilesInDirectory(dir, "part")
  }
}
