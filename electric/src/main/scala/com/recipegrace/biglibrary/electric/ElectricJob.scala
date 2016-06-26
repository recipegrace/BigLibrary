package com.recipegrace.biglibrary.electric


import com.recipegrace.biglibrary.electric.jobs.ArgumentsToMap
import com.recipegrace.biglibrary.electric.spark.SparkContextCreator
import com.thoughtworks.paranamer.{AdaptiveParanamer}
import com.typesafe.scalalogging.slf4j.Logger
import org.apache.spark.SparkContext
import org.slf4j.LoggerFactory

import scala.reflect.ClassTag
case class ElectricContext(isLocal: Boolean, sparkContext: SparkContext)
trait SequenceFileJob[T] extends ElectricJob[T] with FileAccess
abstract class ElectricJob[T:ClassTag] extends SparkContextCreator with ArgumentsToMap  {




  def execute(t: T)(implicit ec: ElectricContext): Unit

  def main(args: Array[String]) = {
    run(args, false)
  }



  def argumentsToObject(args:Array[String]): T = {
    import scala.reflect._
    val clazz = classTag[T].runtimeClass
    val paranamer = new AdaptiveParanamer()
    val constructors = clazz.getConstructors()
    assert(constructors.size == 1, "only one contructor allowed for input argument " + clazz)
    val constructor = constructors.head
    val types = constructor.getParameterTypes.map(f => f.getTypeName)
    val names = paranamer.lookupParameterNames(constructor)
    val arguments = convertArgsToArgs(args, names.zip(types))
    val instance = constructor.newInstance(arguments: _*).asInstanceOf[T]
    instance
  }


  def run(args: Array[String], isLocal: Boolean): Unit = {

    val instance: T = argumentsToObject(args)
    run(instance, isLocal)
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

  def jobName: String = this.getClass.getName

  def runLocal(args: T) = {
    run(args, true)
  }



}
