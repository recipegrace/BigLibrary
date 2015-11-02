package com.recipegrace.biglibrary.electricexamples

import com.cybozu.labs.langdetect.DetectorFactory

/**
 * Created by Ferosh Jacob on 10/30/15.
 */
object ExampleApp extends App{

  val f = "hello world"
  DetectorFactory.loadProfile("/Users/associate/Downloads/profiles")
  val detector = DetectorFactory.create()
  detector.append(f)
  println(detector.detect())
}
