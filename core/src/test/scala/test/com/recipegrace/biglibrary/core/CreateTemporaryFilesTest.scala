package test.com.recipegrace.biglibrary.core

import java.io.File

import com.recipegrace.biglibrary.core.{BaseTest, CreateTemporaryFiles}

/**
 * Created by ferosh on 9/25/15.
 */
class CreateTemporaryFilesTest extends BaseTest with CreateTemporaryFiles {


  test("test temporary files") {
    val tempFolder = ".tests"
    val dir = new File(tempFolder)
    if (dir.exists)
      for (file <- dir.listFiles()) file.delete()

    val tempFile = createOutPutFile(true)

    val text = "hola"

    createFile(text, tempFile)
    val lines = readFilesInDirectory(tempFolder)

    lines should contain(text)

  }

}
