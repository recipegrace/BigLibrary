package com.recipegrace.biglibrary.core

import java.io._
import java.nio.charset.{Charset, StandardCharsets}
import java.nio.file.{Files, Paths}
import java.util.zip.{ZipEntry, ZipInputStream}

import com.typesafe.scalalogging.slf4j.Logger
import org.slf4j.LoggerFactory

import scala.collection.JavaConversions._
import scala.util.Random

/**
  * Created by fjacob on 09/25/15.
  */
trait CreateTemporaryFiles {

  val logger = Logger(LoggerFactory.getLogger("CreateFile"))
  val random = new Random(System.currentTimeMillis())

  def readFilesInDirectory(directory: String, prefix: String = "", charset: Charset = StandardCharsets.US_ASCII): List[String] = {

    val files = new java.io.File(directory).listFiles.filter(_.getName.startsWith(prefix))

    val allFilesContent = for (file <- files)
      yield Files.readAllLines(Paths.get(file.getAbsolutePath), charset)

    allFilesContent.flatten.toList
  }

  def createFile(content: String): String = {
    val path = createTempPath()
    createFileWithContent(content.split("\\r?\\n"), path)

    Thread.sleep(500)
    path
  }

  def createFileWithContent(lines: Seq[String], fileName: String): Unit = {

    val filePath = new PrintWriter(fileName)
    lines.foreach(f => filePath.println(f.trim))
    filePath.close()

  }

  def createFileWithContent(bytes: Array[Byte], fileName: String): Unit = {

    import java.io.FileOutputStream
    val stream = new FileOutputStream(fileName)
    try
      stream.write(bytes)
    finally stream.close()

  }

  def createTempPath(): String = {
    createOutPutFile(false)
  }
  def createDataPath(): String = {
    createOutPutFile(folder = ".data")
  }

  def createOutPutFile(createFile: Boolean = true,isDirectory:Boolean=false, folder:String = ".tests"): String = {

    val temporaryDirectory = folder
    val directory = new File(temporaryDirectory)

    if (!directory.exists()) new File(temporaryDirectory).mkdir()

    val outFile = if (createFile) {
      val tempFile =  if(! isDirectory) Files.createTempFile(Paths.get(temporaryDirectory), "out", getFileName)
        else Files.createTempDirectory(getFileName)
      tempFile.toAbsolutePath.toString

    } else {
      val tempFile = new File(temporaryDirectory, "out" + getFileName)
      tempFile.getAbsoluteFile.toPath.toString
    }
    logger.info(s"OutFile:$outFile")

    outFile
  }

  def getFileName: String = {
    System.currentTimeMillis() + "" + random.nextFloat()
  }


}
