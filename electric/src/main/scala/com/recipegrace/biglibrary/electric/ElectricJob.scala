package com.recipegrace.biglibrary.electric

import com.recipegrace.biglibrary.core.ParameterizedJob
import com.recipegrace.biglibrary.electric.spark.SparkSessionCreator
import org.apache.spark.sql.SparkSession
import scala.reflect.ClassTag
import com.typesafe.scalalogging.Logger
trait SequenceFileJob[T] extends ElectricJob[T]
abstract class ElectricJob[T: ClassTag]
    extends ParameterizedJob[T]
    with SparkSessionCreator {

  def execute(t: T)(implicit ec: ElectricSession): Unit

  def run(args: T, isLocal: Boolean): Unit = {
    val logger = Logger("ElectricJob")
    val t0 = System.currentTimeMillis()
    logger.info("starting job:" + jobName)

    val sc = createSparkSession(isLocal, jobName)
    implicit val context = new ElectricSession(isLocal, sc)
    execute(args)(context)
    sc.stop()
    val t1 = System.currentTimeMillis()
    logger.info("Elapsed time: " + (t1 - t0) + "ms")
  }

}
