package com.recipegrace.biglibrary.electricexamples

import com.cybozu.labs.langdetect.DetectorFactory
import com.recipegrace.biglibrary.core.CreateTemporaryFiles
import com.recipegrace.biglibrary.electric.ElectricContext
import com.recipegrace.biglibrary.electric.jobs.{SimpleJob, TwoInputJob}
import org.apache.spark.SparkContext._
import org.apache.spark.SparkFiles

/**
 * Created by Ferosh Jacob on 10/30/15.
 */
object CountTokens extends SimpleJob {


  override def execute(input:String,output:String)(implicit ec: ElectricContext): Unit = {

     val content = ec.sparkContext.textFile(input)

                 .filter(f=> f.split("\\t",-1).size==4)
                  .map(f=> {
                    val parts =f.split("\\t", -1)
                    (parts(1),parts(2))
                  })


                  .filter(f=> f._1.trim.length>0 && f._2.forall(_.isDigit))
                  .map(f=> (f._1,f._2.toLong))
                  .map(f=> (f._1.split("\\s",-1).size+"", f._2))

       .reduceByKey(_ + _)
       .map(f => f._1 + "\t" + f._2)

    writeFile(content, output)
  }
}
