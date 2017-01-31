package com.recipegrace.biglibrary.electricexamples

import java.io.{File, FileInputStream}
import java.nio.file.Paths

import com.cybozu.labs.langdetect.DetectorFactory
import com.recipegrace.biglibrary.core.CreateTemporaryFiles
import com.recipegrace.biglibrary.core.ZipArchive
import com.recipegrace.biglibrary.electric.{SequenceFileJob, ElectricContext}

import org.apache.spark.SparkFiles

/**
  * Created by Ferosh Jacob on 10/30/15.
  */
object LanguageDetectWrapper extends ZipArchive {
  val path = SparkFiles.get("profiles.zip")
  val profileFolder = unZip(path)
  DetectorFactory.loadProfile(profileFolder)
  println("loaded profiles...")

  def detectLanguage(text:String) = {
    val detector = DetectorFactory.create()

    detector.append(text)
    detector.detect()
  }
}

object LanguageDetect extends SequenceFileJob[InputsAndOutput] with CreateTemporaryFiles with ZipArchive  {



  override def execute(args:InputsAndOutput)(implicit ec: ElectricContext): Unit = {


    ec.sparkContext.addFile(args.input2)




    val content = readFile(args.input1)
      .map(f => {

        (LanguageDetectWrapper.detectLanguage(f), f)
      })
      .map(f => f._1 + "\t" + f._2 )

    writeFile(content, args.output)
  }
}
