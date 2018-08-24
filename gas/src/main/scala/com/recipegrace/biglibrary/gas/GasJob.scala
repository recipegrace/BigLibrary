package com.recipegrace.biglibrary.gas

import java.nio.file.Paths

import com.google.cloud.storage.Storage
import com.recipegrace.biglibrary.core.{CreateTemporaryFiles, ParameterizedJob}
import com.recipegrace.biglibrary.gas.gcloud.GoogleCloudStorage
import com.typesafe.scalalogging.slf4j.Logger
import org.slf4j.LoggerFactory

import scala.reflect.ClassTag

case class GasSession(isLocal:Boolean,storage: Storage)
case class GCSInputLocation(inputFile:String)
case class GCSOutputLocation(outputFile:String){
  var bucket:String =""
  var objectName:String = ""
  def setBucket(x:String ) = {
    bucket=x
  }
  def setObjectName(x:String ) = {
    objectName=x
  }
}

abstract class GasJob[T:ClassTag] extends ParameterizedJob[T]   with CreateTemporaryFiles {

  def execute(t: T)(implicit gs:GasSession) :Unit

  def run(args: T, isLocal: Boolean): Unit = {
    val logger = Logger(LoggerFactory.getLogger("GasJob"))
    val t0 = System.currentTimeMillis()
    logger.info("starting job:" + jobName)
    implicit val context = new GasSession(isLocal,GoogleCloudStorage.getStorage)
    execute(args)(context)
    val t1 = System.currentTimeMillis()
    logger.info("Elapsed time: " + (t1 - t0) + "ms")
  }

  override def toArgument(parameterName:String, parameterType:String, argumentValues:Map[String,String],isLocal:Boolean, isLevelOne:Boolean=false) = {
    assert(!parameterName.startsWith("$"), "the Argument class should be declared at package scope, not an inner scope")
    val argValue = getArgumentValues(parameterName,argumentValues)
    parameterType match {
      case "com.recipegrace.biglibrary.gas.GCSInputLocation"  if isLocal => {
        GCSInputLocation (argValue)
      }
      case "com.recipegrace.biglibrary.gas.GCSOutputLocation" if isLocal  => {
        GCSOutputLocation (argValue)
      }
      case "com.recipegrace.biglibrary.gas.GCSOutputLocation" if !isLocal  => {
        assert(argValue.startsWith("gs://"), s"$parameterName defined as google location in non-local should have a gcs path")
        val (bucket: String, objectName: String) = extractBucketAndObjectName(parameterName, argValue)
        val location = GCSOutputLocation (argValue)
        location.setBucket(bucket)
        location.setObjectName(objectName)
        location
      }
      case "com.recipegrace.biglibrary.gas.GCSInputLocation" => {

        assert(argValue.startsWith("gs://"), s"$parameterName defined as google location in non-local should have a gcs path")
        val path = createDataPath()
        val (bucket: String, objectName: String) = extractBucketAndObjectName(parameterName, argValue)
        GoogleCloudStorage.download(bucket,objectName,Paths.get(path))
        GCSInputLocation (path.toString)
      }
      case _ => super.toArgument(parameterName,parameterType,argumentValues,isLevelOne)
    }
  }

  def upload(content:Array[Byte], gcsPath: GCSOutputLocation)(implicit gs:GasSession) = {

    if(!gs.isLocal) {
      val path =createDataPath
      createFileWithContent(content,path)
      GoogleCloudStorage.uploadFile(gcsPath.bucket,gcsPath.objectName,content)
    } else
      createFileWithContent(content,gcsPath.outputFile)
  }

  private def extractBucketAndObjectName(parameterName: String, argValue: String) = {
    val (bucket, objectName) = argValue.substring(5).split("/", -1).toList match {
      case x :: y if y.forall(f => f.nonEmpty) => (x, y.mkString("/"))
      case _ => {
        assert(false, s"invalid value for $parameterName, cannot be empty or just the bucket name")
        ("", "")
      }
    }
    (bucket, objectName)
  }
}
