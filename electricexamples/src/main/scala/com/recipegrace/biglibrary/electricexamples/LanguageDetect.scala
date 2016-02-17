package com.recipegrace.biglibrary.electricexamples

import com.cybozu.labs.langdetect.DetectorFactory
import com.recipegrace.biglibrary.core.CreateTemporaryFiles
import com.recipegrace.biglibrary.core.ZipArchive
import com.recipegrace.biglibrary.electric.ElectricContext
import com.recipegrace.biglibrary.electric.jobs.TwoInputJob

import org.apache.spark.SparkFiles

/**
  * Created by Ferosh Jacob on 10/30/15.
  */
object LanguageDetectWrapper extends ZipArchive {
  val profileFolder = unZip(SparkFiles.get("profiles.zip"))
  DetectorFactory.loadProfile(profileFolder)
  println("loaded profiles...")

  def detectLanguage(text:String) = {
    val detector = DetectorFactory.create()

    detector.append(text)
    detector.detect()
  }
}

object LanguageDetect extends TwoInputJob with CreateTemporaryFiles with ZipArchive  {



  override def execute(one: String, two: String, output: String)(implicit ec: ElectricContext): Unit = {


    ec.sparkContext.addFile(two)




    val content = readFile(one)
      .map(f => {

        (LanguageDetectWrapper.detectLanguage(f), f)
      })
      .map(f => f._1 + "\t" + f._2 )

    writeFile(content, output)
  }
}
