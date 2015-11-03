package com.recipegrace.biglibrary.electricexamples

import com.cybozu.labs.langdetect.DetectorFactory
import com.recipegrace.biglibrary.core.CreateTemporaryFiles

/**
 * Created by Ferosh Jacob on 10/30/15.
 */
object ExampleApp extends App with CreateTemporaryFiles{

  val f = "hello world"

  val unzipped = unZipFile("/Users/associate/tools/lang-detect/profiles.zip")

  println("sddsdsds"+unzipped)
  DetectorFactory.loadProfile(unzipped)
  val detector = DetectorFactory.create()
  detector.append(f)
  println(detector.detect())
}
