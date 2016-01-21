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
  println(matcher1.distance(f1, f2))

  val matcher2 = new JaroWinkler()
  println(matcher2.distance(f1, f2))

  val matcher3 = new MetricLCS()
  println(matcher3.distance(f1, f2))

  val matcher4 = new NGram()
  println(matcher4.distance(f1, f2))

  val matcher5 = new Cosine()
  println(matcher5.distance(f1, f2))

  val matcher6 = new Jaccard()
  println(matcher6.distance(f1, f2))


  val matcher7 = new SorensenDice()
  println(matcher7.distance(f1, f2))


}
