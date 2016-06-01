
# BigLibrary

[![Build Status](https://travis-ci.org/recipegrace/BigLibrary.svg?branch=master)](https://travis-ci.org/recipegrace/BigLibrary)


<h3>WORA (Write Once Run Anywhere) framework</h3>
The Biglibrary has been designed as a wrapper for bigdata programs (currently implemented for SPARK). 
The library enables programmers 1) to customize execution for local and cluster modes, 2) functions for boiler plate code, and 3) guarantees deployable code.

Biglibrary realize a bigdata program as a pair: 1) Actual job and 2) Test job. The actual job can be executed in a cluster using the 
<a href="https://github.com/recipegrace/ScriptDB">ScriptDB</a>.  Few examples implemented using the BigLibrary  are given below.

<b>WordCount</b>


```scala
object WordCount extends SimpleJob {
  override def execute(input: String, output: String)(ec: ElectricContext) = {
    implicit val context = ec
    val file = readFile(input)
    val words = file.flatMap(_.toLowerCase.replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+"))
    val wordCounts = words
      .map(x => (x, 1)).reduceByKey(_ + _)
      .map(f => f._1 + "\t" + f._2)
    writeFile(wordCounts, output)
  }

```  
WordCountTest 

```scala
class WordCountTest extends SimpleJobTest {
  test("wordcount test with spark") {
    val input = createFile {
      """
        hello world
        Zero world
        Some world
      """.stripMargin
    }
    val output = createTempPath()
    launch(WordCount, TwoArgument(input, output))

    val lines = readFilesInDirectory(output, "part")
    lines should contain("hello\t1")
    lines should contain("world\t3")
  }
```

# Developers 

To publish a new version

1. Create a OSS account
2. Checkout project and make changes
3. ./sbt + publish-signed



