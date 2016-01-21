package com.recipegrace.biglibrary.electricexamples

import com.cybozu.labs.langdetect.DetectorFactory
import com.recipegrace.biglibrary.core.CreateTemporaryFiles
import com.recipegrace.biglibrary.electric.ElectricContext
import com.recipegrace.biglibrary.electric.jobs.TwoInputJob
import org.apache.spark.SparkFiles

/**
  * Created by Ferosh Jacob on 10/30/15.
  */
object LanguageDetect extends TwoInputJob with CreateTemporaryFiles  {


  override def execute(one: String, two: String, output: String)(implicit ec: ElectricContext): Unit = {


    ec.sparkContext.addFile(two)




    val content = readFile(one)
      .map(f => {
        val lang =
          try {
            if (DetectorFactory.getLangList.size() < 1) {
              val profileFolder = unZipFile(SparkFiles.get("profiles.zip"))
              DetectorFactory.loadProfile(profileFolder)
            }
            val detector = DetectorFactory.create()

            detector.append(f)
            detector.detect()
          }
          catch {
            case e: Throwable => {
              "ND"
            }
          }
        (lang, f)
      })
      .map(f => f._1 + "\t" + f._2 )

    writeFile(content, output)
  }
}
