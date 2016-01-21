package com.recipegrace.biglibrary.electric

import com.recipegrace.biglibrary.electric.spark.SparkContextCreator
import com.typesafe.scalalogging.slf4j.Logger
import org.apache.spark.{SparkConf, SparkContext}
import org.slf4j.LoggerFactory

/**
 * Created by ferosh on 9/25/15.
 */

case class ElectricContext(isLocal: Boolean, sparkContext: SparkContext)

trait ElectricJob[T] extends SparkContextCreator {



  def jobName: String = this.getClass.getName

  def toIterator(t: Product): Iterator[Any] = t.productIterator.flatMap {
    case p: Product => toIterator(p)
    case x => Iterator(x)
  }

  def execute(t: T)(implicit sc: ElectricContext)


  def main(args: Array[String]) = {

    run(args, false)
  }

  def toArray(args: Map[String, Any]): Array[String] = {
    args.toList.flatMap(f => List("--" + f._1, f._2.toString)).toArray
  }

  def runLocal(args: Map[String, Any]) = {



    run(toArray(args), true)

  }

  def runLocal(args: T) = {

    run(args, true)
  }

  def run(args: T, isLocal: Boolean): Unit = {
    val logger = Logger(LoggerFactory.getLogger("ElectricJob"))
    val t0 = System.currentTimeMillis()
    logger.info("starting job:" + jobName)

    val sc = createSparkContext(isLocal, jobName)
    implicit val context = ElectricContext(isLocal, sc)
    execute(args)(context)
    sc.stop()
    val t1 = System.currentTimeMillis()
    logger.info("Elapsed time: " + (t1 - t0) + "ms")
  }

  def parse(args:Array[String]) :T

  def run(args: Array[String], isLocal: Boolean): Unit = {
        run(parse(args), isLocal)
  }

}
