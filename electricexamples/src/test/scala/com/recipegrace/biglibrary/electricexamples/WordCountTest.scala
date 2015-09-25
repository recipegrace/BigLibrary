package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.ElectricJobTest


/**
 * Created by fjacob on 9/25/15.
 */
class WordCountTest extends ElectricJobTest {

  test("wordcount test with spark") {

    val input = createOutPutFile()
    createFile("hello world", input)
    val output = createOutPutFile(false)
    launch(WordCount, Map("input" -> input, "output" -> output))

    val lines = readFilesInDirectory(output, "part")
    lines should contain("hello\t1")
    lines should contain("world\t1")
  }

}
