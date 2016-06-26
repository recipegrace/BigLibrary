package com.recipegrace.biglibrary.core

import java.io.File


import scala.io.Source

/**
  * Created by ferosh on 9/25/15.
  */
class CreateTemporaryFilesTest extends BaseTest with CreateTemporaryFiles with StringCompare with ZipArchive {


  test("test temporary files") {


    val text = "hola"

    val path = createFile(text)
    val lines = Source.fromFile(path).getLines().toList
    lines should contain(text)

  }
  test("zip file test 1") {
    val zipFile=this.getClass.getResourceAsStream("/hello.zip")

    val path =unZip(zipFile)
    val file = new File(path).listFiles().head
    Source.fromFile(file).getLines().toList.head should equal("hello")
  }

  test("zip file test 2") {
  }

  test("string oneway compare") {
    val value = onewayCompare("This is a hello world", "Hello worlds")
    logger.info(s"**********$value")
  }

  test("string compare") {
    val value = compare("something to think about hello", "hello worlds")
    logger.info(value+"")
  }
}
