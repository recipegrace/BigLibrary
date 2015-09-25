package com.recipegrace.biglibrary.electric

import com.recipegrace.biglibrary.core.CreateTemporaryFiles

/**
 * Created by fjacob on 9/25/15.
 */
trait Launcher extends CreateTemporaryFiles {


  def launch(sparkJob: ElectricJob, args: Map[String, String]) = {
    sparkJob.runLocal(args)
  }

  def readSparkOut(dir: String) = {
    readFilesInDirectory(dir, "part")
  }
}
