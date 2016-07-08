package com.recipegrace.biglibrary.core

/*
 * credit to Swind/ZipArchive.scala   https://gist.github.com/Swind/2601206
 */

import java.io._
import java.util.zip.{ZipEntry, ZipInputStream}

trait ZipArchive extends CreateTemporaryFiles {


  def unZip(zipFile: String) = {
    val tempPath = createTempPath()
    unZipToFolder(zipFile, tempPath)
    tempPath
  }

  def unZipToFolder(inputStream: InputStream, outputFolder: String): Unit = {
    try {
      val buffer = new Array[Byte](1024)
      val zis: ZipInputStream = new ZipInputStream(inputStream)


      //output directory
      val folder = new File(outputFolder)
      if (!folder.exists()) {
        folder.mkdir()
      }
      //get the zipped file list entry
      var ze: ZipEntry = zis.getNextEntry()

      while (ze != null) {

        val fileName = ze.getName()
        val newFile = new File(outputFolder + File.separator + fileName)

        //  System.out.println("file unzip : " + newFile.getAbsoluteFile());

        //create folders
        new File(newFile.getParent()).mkdirs()

        val fos = new FileOutputStream(newFile)

        var len: Int = zis.read(buffer)

        while (len > 0) {

          fos.write(buffer, 0, len)
          len = zis.read(buffer)
        }

        fos.close()
        ze = zis.getNextEntry()
      }

      zis.closeEntry()
      zis.close()

    } catch {
      case e: IOException => println("exception caught: " + e.getMessage)
    }

  }

  def unZipToFolder(zipFile: String, outputFolder: String): Unit = {
    unZipToFolder(new FileInputStream(zipFile), outputFolder)
  }

  def unZip(inputStream: InputStream) = {
    val tempPath = createTempPath()
    unZipToFolder(inputStream, tempPath)
    tempPath
  }

}
