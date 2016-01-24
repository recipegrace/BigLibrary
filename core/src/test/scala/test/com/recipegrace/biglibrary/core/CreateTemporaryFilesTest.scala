package test.com.recipegrace.biglibrary.core

import java.io.File

import com.recipegrace.biglibrary.core.{BaseTest, CreateTemporaryFiles, StringCompare}

import scala.io.Source

/**
  * Created by ferosh on 9/25/15.
  */
class CreateTemporaryFilesTest extends BaseTest with CreateTemporaryFiles with StringCompare {


  test("test temporary files") {


    val text = "hola"

    val path = createFile(text)
    val lines = Source.fromFile(path).getLines().toList
    lines should contain(text)

  }

  test("zip file test") {
    val hello = unZipFile( this.getClass.getResourceAsStream("/hello.zip"))
    println(hello)
    val file = new File(hello).listFiles().head
    Source.fromFile(file).getLines().toList.head should equal("hello")
  }

  test("string oneway compare") {
    val value = onewayCompare("This is a hello world", "Hello worlds")
    println(s"**********$value")
  }

  test("string compare") {
    val value = compare("something to think about hello", "hello worlds")
    println(value)
  }
}
