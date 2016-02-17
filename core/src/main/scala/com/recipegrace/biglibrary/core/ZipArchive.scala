package com.recipegrace.biglibrary.core
/*
 * credit to Swind/ZipArchive.scala   https://gist.github.com/Swind/2601206
 */
import java.io._
import java.util.zip.{ZipEntry, ZipFile, ZipInputStream}

import scala.collection.JavaConversions._
import scala.io.Source

trait ZipArchive extends CreateTemporaryFiles {

  val BUFSIZE = 4096
  val buffer = new Array[Byte](BUFSIZE)

  def unZip(source: String):String = {
    val zipFile = new ZipFile(source)

    val outputPath = createOutPutFile(true,true)
    unzipAllFile(zipFile.entries.toList, getZipEntryInputStream(zipFile), new File(outputPath))
    logger.info("Unzipped to folder:" +outputPath )
    outputPath
  }
  def unZip(source: InputStream):String = {

    val zis = new ZipInputStream(source)
    var zipEntry = zis.getNextEntry
    var map = Map[ZipEntry, String]()
    var entries = List():List[ZipEntry]
    while(zipEntry!=null) {

      val content=Source.fromInputStream(zis,"ISO-8859-1").getLines().mkString("\n")
     // println("con.."+content)
      if(!zipEntry.isDirectory)  map = map++Map(zipEntry -> content)
      entries = zipEntry:: entries
      zipEntry = zis.getNextEntry


    }
    val outPath =createTempPath()
    val outFile =new File(outPath)
    outFile.mkdir()
    unzipAllFile(entries.sortBy(f=> f.getName.size), outFile )
    def unzipAllFile(entryList: List[ZipEntry],  targetFolder: File): Boolean = {

      entryList match {
        case entry :: entries => {


          if (entry.isDirectory)
            new File(targetFolder, entry.getName).mkdirs
          else
            saveFile1( targetFolder.getAbsolutePath, entry.getName, entry)

          unzipAllFile(entries,  targetFolder)
        }
        case _ =>
          true
      }

    }
    def saveFile1(folder1:String, folder2:String,entry:ZipEntry) = {

      val writer = new FileWriter(folder1+"/"+ folder2)
      writer.append(map(entry))
      writer.close()
    }

    zis.close()
    outPath
  }

  def getZipEntryInputStream(zipFile: ZipFile)(entry: ZipEntry) = zipFile.getInputStream(entry)



  def unzipAllFile(entryList: List[ZipEntry], inputGetter: (ZipEntry) => InputStream, targetFolder: File): Boolean = {

    entryList match {
      case entry :: entries =>

        if (entry.isDirectory)
          new File(targetFolder, entry.getName).mkdirs
        else
          saveFile(inputGetter(entry), new FileOutputStream(new File(targetFolder, entry.getName)))

        unzipAllFile(entries, inputGetter, targetFolder)
      case _ =>
        true
    }

  }


  def saveFile(fis: InputStream, fos: OutputStream) = {
    writeToFile(bufferReader(fis)_, fos)
    fis.close
    fos.close
  }

  def bufferReader(fis: InputStream)(buffer: Array[Byte]) = (fis.read(buffer), buffer)

  def writeToFile(reader: (Array[Byte]) => Tuple2[Int, Array[Byte]], fos: OutputStream): Boolean = {
    val (length, data) = reader(buffer)
    if (length >= 0) {
      fos.write(data, 0, length)
      writeToFile(reader, fos)
    } else
      true
  }
}
