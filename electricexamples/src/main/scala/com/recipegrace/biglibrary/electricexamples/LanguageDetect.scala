package com.recipegrace.biglibrary.electricexamples

import com.cybozu.labs.langdetect.DetectorFactory
import com.recipegrace.biglibrary.electric.ElectricContext
import com.recipegrace.biglibrary.electric.jobs.{TwoInputJob, SimpleJob}
import org.apache.spark.SparkContext._
/**
 * Created by Ferosh Jacob on 10/30/15.
 */
object LanguageDetect extends TwoInputJob {


  override def execute(one: String,two:String, output: String)(implicit ec: ElectricContext): Unit = {
    implicit val sc =ec
   DetectorFactory.loadProfile(two)
   val detector = DetectorFactory.create()
    val contextDetector=sc.sparkContext.broadcast(detector)
     val content = ec.sparkContext.textFile(one)
                  .map(f=> {
                    val parts =f.split("\\t", -1)
                    (parts(1),parts(2))
                  })

                  .filter(f=> f._1.trim.length>0 && f._2.forall(_.isDigit))
                  .map(f=> (f._1,f._2.toLong))
                   .map(f=>{
                     val lang= try {
                     DetectorFactory.clear()

                    contextDetector.value.append(f._1)
                    contextDetector.value.detect()
                     }
                      catch {
                        case _ => "ND"
                      }
                     (lang,f._2)
                   })
       .reduceByKey(_ + _)
       .map(f => f._1 + "\t" + f._2)

    writeFile(content, output)
  }
}
