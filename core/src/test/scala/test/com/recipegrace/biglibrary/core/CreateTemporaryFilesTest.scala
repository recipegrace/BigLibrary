package test.com.recipegrace.biglibrary.core

import com.recipegrace.biglibrary.core.{BaseTest, CreateTemporaryFiles}

/**
 * Created by ferosh on 9/25/15.
 */
class CreateTemporaryFilesTest extends BaseTest with CreateTemporaryFiles {


  test("test temporary files") {


    val text = "hola"

    val path =createFile(text)
    val lines = readFilesInDirectory(".tests")
    lines should contain(text)

  }

}
