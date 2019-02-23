
# BigLibrary

[![Build Status](https://travis-ci.org/recipegrace/BigLibrary.svg?branch=master)](https://travis-ci.org/recipegrace/BigLibrary)




<h3>WORA (Write Once Run Anywhere) framework</h3>
The Biglibrary has been designed as a wrapper for bigdata programs (currently implemented for SPARK). 
The library enables programmers 1) to customize execution for local and cluster modes, 2) functions for boiler plate code, and 3) guarantees deployable code.

Biglibrary realize a bigdata program as a pair: 1) Actual job and 2) Test job. The actual job can be executed in a cluster using the 
<a href="https://github.com/recipegrace/ScriptDB">ScriptDB</a>.  Few examples implemented using the BigLibrary  are given below.

<b>WordCount</b>


```scala
object WordCount extends SequenceFileJob[InputAndOutput] {
  override def execute(argument: InputAndOutput)(implicit ec: ElectricSession) = {
    val session = ec.getSparkSession

    import session.implicits._
    val file = ec.text(argument.input)
    val words = file.flatMap(_.toLowerCase.replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+"))
    words
      .groupByKey(f => f)
      .count()
      .write
      .option("delimiter", "\t")
      .csv(argument.output)

  }

}
```  
WordCountTest 

```scala
class WordCountTest extends ElectricJobTest {
  test("wordcount test with spark") {
    val input = createFile {
      """
        hello world
        Zero world
        Some world
      """.stripMargin
    }
    val output = createTempPath()
    launch(WordCount, InputAndOutput(input, output))
    val lines = readFilesInDirectory(output, "part")
    lines should contain("hello\t1")
    lines should contain("world\t3")
  }
}
```

# Developers 

[OSS + travis+ sbt](https://github.com/recipegrace/BigLibrary/blob/master/doc/Oss-publish-travis.md)




