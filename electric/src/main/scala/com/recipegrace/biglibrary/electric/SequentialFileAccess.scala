package com.recipegrace.biglibrary.electric

import com.recipegrace.biglibrary.core.TableDefinition
import org.apache.hadoop.io.{LongWritable, Text}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

import scala.reflect.ClassTag

/**
 * Created by ferosh on 9/25/15.
 */
trait SequentialFileAccess extends ElectricJob {

  def sequentialFile[P: ClassTag](hiveTable: TableDefinition[P], isTextFile: Boolean = false)(implicit ec: ElectricContext): RDD[P] = {


    val content = getSequentialFileText(ec.isLocal, isTextFile, hiveTable, ec.sparkContext)
    if (ec.isLocal)
      content
        .map(hiveTable.columns.local)
    else content.map(hiveTable.columns.remote)

  }

  private def getSequentialFileText[P](isLocal: Boolean, isRemoteTextFile: Boolean, table: TableDefinition[P], sc: SparkContext): RDD[String] = {

    if (isLocal)
      sc
        .textFile(table.tableName.local)

    else if (!isLocal && isRemoteTextFile)
      sc
        .textFile(table.tableName.remote)

    else {
      sc
        .sequenceFile(table.tableName.remote, classOf[LongWritable], classOf[Text])
        .map(f => f._2.toString)

    }
  }
  def readFile(file:String)(implicit sc: ElectricContext):RDD[String] ={
    readFile(file,true)
  }
  def readFile(file:String, isSequence:Boolean)(implicit sc: ElectricContext):RDD[String] ={

    if(sc.isLocal|| !isSequence) {
      sc.sparkContext.textFile(file)
    }else {
      sc.sparkContext.sequenceFile[Text,Text](file)
        .map(f=> f._2.toString)

    }
  }


  def writeFile(result:RDD[String],file:String)(implicit sc: ElectricContext) ={
    if(sc.isLocal) result.saveAsTextFile(file)
    else {
      result.map(f=> ("",f))
        .saveAsSequenceFile(file)
    }
  }

}
