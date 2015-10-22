package com.recipegrace.biglibrary.electric

import com.typesafe.scalalogging.slf4j.Logger
import org.apache.spark.{SparkConf, SparkContext}
import org.slf4j.LoggerFactory

/**
 * Created by ferosh on 9/25/15.
 */

case class ElectricContext(isLocal: Boolean, sparkContext: SparkContext)

trait ElectricJob[T] {



  def jobName: String = this.getClass.getName

  def toIterator(t: Product): Iterator[Any] = t.productIterator.flatMap {
    case p: Product => toIterator(p)
    case x => Iterator(x)
  }

  def job(t: T)(implicit sc: ElectricContext)


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
    logger.info("starting job:" + jobName)
    val jars = if (isLocal) List() else List(SparkContext.jarOfObject(this).get)

    val sc: SparkContext = {
      val conf = new SparkConf().setAppName(jobName).setJars(jars)
      if (isLocal)
        conf.setMaster("local")
      new SparkContext(conf)
    }
    implicit val context = ElectricContext(isLocal, sc)
    job(args)(context)
    sc.stop()
  }

  def parse(args:Array[String]) :T

  def run(args: Array[String], isLocal: Boolean): Unit = {
        run(parse(args), isLocal)
  }

}
