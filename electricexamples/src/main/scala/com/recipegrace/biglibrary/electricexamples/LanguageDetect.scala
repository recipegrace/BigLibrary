package com.recipegrace.biglibrary.electricexamples

import com.cybozu.labs.langdetect.DetectorFactory
import com.recipegrace.biglibrary.core.CreateTemporaryFiles
import com.recipegrace.biglibrary.electric.ElectricContext
import com.recipegrace.biglibrary.electric.jobs.TwoInputJob
import org.apache.spark.SparkFiles

/**
  * Created by Ferosh Jacob on 10/30/15.
  */
object LanguageDetect extends TwoInputJob with CreateTemporaryFiles with Parser {


  override def execute(one: String, two: String, output: String)(implicit ec: ElectricContext): Unit = {


    val latinLangs = List("en", "nl", "af", "it", "da", "fr", "ro", "no", "so", "sv", "de", "tl", "es", "fi", "id", "et", "pt", "sq", "lt", "lv", "sl", "sk", "sw", "tr", "cs", "hr", "vi", "hu")
    ec.sparkContext.addFile(two)




    val content = inputParse(one)
      .map(f => {
        val lang =
          try {
            if (DetectorFactory.getLangList.size() < 1) {
              val profileFolder = unZipFile(SparkFiles.get("profiles.zip"))
              DetectorFactory.loadProfile(profileFolder)
            }
            val detector = DetectorFactory.create()

            detector.append(f._1)
            detector.detect()
          }
          catch {
            case e: Throwable => {
              "ND"
            }
          }
        (lang, f._1, f._2)
      })
      // .reduceByKey(_ + _)
      .filter(f => !latinLangs.contains(f._1))
      .map(f => f._1 + "\t" + f._2 + "\t" + f._3)

    writeFile(content, output)
  }
}
