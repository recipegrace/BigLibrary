package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.core.CreateTemporaryFiles

import info.debatty.java.stringsimilarity._

/**
  * Created by Ferosh Jacob on 10/30/15.
  */
object ExampleApp extends App with CreateTemporaryFiles {

  val f1 = "hello world"

  val f2 = "hello"

  val matcher1 = new NormalizedLevenshtein()
  logger.info(matcher1.distance(f1, f2) + "")

  val matcher2 = new JaroWinkler()
  logger.info(matcher2.distance(f1, f2) + "")

  val matcher3 = new MetricLCS()
  logger.info(matcher3.distance(f1, f2) + "")

  val matcher4 = new NGram()
  logger.info(matcher4.distance(f1, f2) + "")

  val matcher5 = new Cosine()
  logger.info(matcher5.distance(f1, f2) + "")

  val matcher6 = new Jaccard()
  logger.info(matcher6.distance(f1, f2) + "")

  val matcher7 = new SorensenDice()
  logger.info(matcher7.distance(f1, f2) + "")

}
