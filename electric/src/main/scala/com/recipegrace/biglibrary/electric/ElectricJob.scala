package com.recipegrace.biglibrary.electric

import com.recipegrace.biglibrary.core.{Argument, ParseArguments}
import org.apache.spark.{SparkConf, SparkContext}


/**
 * Created by ferosh on 9/25/15.
 */

case class ElectricContext( isLocal:Boolean, sparkContext: SparkContext)

trait ElectricJob  extends ParseArguments{

  val namedArguments:Set[Argument]=Set()

  def jobName: String = this.getClass.getName

  def toIterator(t: Product): Iterator[Any] = t.productIterator.flatMap {
    case p: Product => toIterator(p)
    case x => Iterator(x)
  }

  def job(args: Map[Argument, String], sc: ElectricContext)

  def parseArgs(strings: Array[String], names:Set[Argument]): Map[Argument, String] = {

    parse(strings,names)
  }

  def main(args: Array[String]) = {

    run(args, false)
  }

  def toArray(args: Map[String, String]): Array[String] = {

    args.toList.flatMap(f => List("--" + f._1, f._2)).toArray
  }

  def runLocal(args: Map[String, String]) = {

    run(toArray(args), true)
  }

  def run(args: Array[String], isLocal: Boolean) = {


    val jars = if (isLocal) List() else List(SparkContext.jarOfObject(this).get)

    val sc: SparkContext = {
      val conf = new SparkConf().setAppName(jobName).setJars(jars)
      if (isLocal)
        conf.setMaster("local")
      new SparkContext(conf)
    }

    val programArguments = parseArgs(args, namedArguments)
    job(programArguments, ElectricContext(isLocal, sc))

  }

}
