package com.recipegrace.biglibrary.core

import info.debatty.java.stringsimilarity._

/**
  * Created by Ferosh Jacob on 11/3/15.
  */
trait StringCompare {


  def onewayCompare(canChange: String, unChanged: String): Double = {
    val strings = canChange.toLowerCase.split("\\s+", -1)

    if (strings.size > 6) {
      if (canChange.contains(unChanged.toLowerCase)) return 1.0
      else return 0.0
    }
    val ngrams =
      for (i <- 1 to 5)
        yield strings.sliding(i).filter(f => f.forall(_.size > 2)).map(f => f.mkString(" "))

    val list = ngrams.flatten.map(f => {
      if (f.equals(unChanged.toLowerCase)) 1.0 else 0.0
    })
    if (list.isEmpty) 0.0 else list.max

  }

  def compare(f1: String, f2: String): List[Double] = {

    // println(s"$f1 and $f2")
    val metrics =
      List(
        new MetricLCS().distance(f1, f2),
        new NGram().distance(f1, f2)
      )

    // println(metrics.mkString(","))
    metrics
  }
}
