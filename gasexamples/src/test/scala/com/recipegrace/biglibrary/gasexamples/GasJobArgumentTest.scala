package com.recipegrace.biglibrary.gasexamples

import com.recipegrace.biglibrary.gas.tests.GasJobTest
import com.recipegrace.biglibrary.gas.{GCSInputLocation, GCSOutputLocation, GasJob, GasSession}
import com.recipegrace.biglibrary.gasexamples

import scala.io.Source


/**
  * Created by fjacob on 9/25/15.
  */
case class InAndOut(input:String, output:Int, input1:Double, flag:Boolean, float1:Float)
case class InAndOutWithCloud(input:GCSInputLocation, output:Int, input1:Double, flag:Boolean, float1:Float, output1:GCSOutputLocation)
class GasJobArgumentTest extends GasJobTest {
  object TestWithoutCloud extends GasJob[InAndOut]{
    override def execute(t: InAndOut)(implicit gs:GasSession): Unit = {}
  }
  object TestWithCloud extends GasJob[InAndOutWithCloud]{
    override def execute(t: InAndOutWithCloud)(implicit gs:GasSession): Unit = {}
  }


  ignore("argument test without cloud") {
    val list = Array("--input", "bob", "--output","22", "--flag","true" , "--input1", "1.4", "--float1", "1.5f")

    val obj = TestWithoutCloud.argumentsToObject(list)
    obj.input  shouldBe "bob"
    obj.output shouldBe 22
    obj.input1 shouldBe 1.4
    obj.flag shouldBe true
    obj.float1 shouldBe 1.5f
    val x = intercept[AssertionError]{
      val list = Array("--input", "bob", "--output","22", "--flag","true" , "--input1", "1.4", "float1", "1.5f")
      val obj = TestWithoutCloud.argumentsToObject(list)
    }
    x.getMessage shouldBe "assertion failed: Unable to parse arguments:--input bob --output 22 --flag true --input1 1.4 float1 1.5f"
  }



  ignore("argument test with cloud") {
    val list = Array("--input", "gs://mosambi/terms.txt", "--algorithm","BLAS", "--output","22", "--flag","true" , "--input1", "1.4",
      "--float1", "1.5f", "--output1", "gs://work/output")

    val obj = TestWithCloud.argumentsToObject(list)
    obj.input should not be GCSInputLocation("gs://mosambi/terms.txt")
    obj.output shouldBe 22
    obj.input1 shouldBe 1.4
    obj.flag shouldBe true
    obj.float1 shouldBe 1.5f
    obj.output1 shouldBe GCSOutputLocation("gs://work/output")

  }
  ignore("argument test with cloud validating tests") {

    def validateGCSPath(list:Array[String], message:String) = {

      val x = intercept[AssertionError] {
        TestWithCloud.argumentsToObject(list)
      }
      x.getMessage shouldBe message

    }

    validateGCSPath(Array("--input", "hola/work", "--algorithm", "BLAS", "--output", "22", "--flag", "true", "--input1", "1.4", "--float1", "1.5f", "--output1", "gs://work/output")
    ,"assertion failed: input defined as google location in non-local should have a gcs path")
    validateGCSPath(Array("--input", "gs://hola/", "--algorithm","BLAS", "--output","22", "--flag","true" , "--input1", "1.4", "--float1", "1.5f", "--output1", "gs://work/output")
,"assertion failed: invalid value for input, cannot be empty or just the bucket name")
    validateGCSPath(Array("--input", "gs://hola//test", "--algorithm","BLAS", "--output","22", "--flag","true" , "--input1", "1.4", "--float1", "1.5f", "--output1", "gs://work/output")
      ,"assertion failed: invalid value for input, cannot be empty or just the bucket name")

    validateGCSPath(Array("--input", "gs://mosambi/terms.txt", "--algorithm", "BLAS", "--output", "22", "--flag", "true", "--input1", "1.4", "--float1", "1.5f", "--output1", "work/output")
      ,"assertion failed: output1 defined as google location in non-local should have a gcs path")
    validateGCSPath(Array("--input", "gs://mosambi/terms.txt", "--algorithm","BLAS", "--output","22", "--flag","true" , "--input1", "1.4", "--float1", "1.5f", "--output1", "gs://work/")
      ,"assertion failed: invalid value for output1, cannot be empty or just the bucket name")
    validateGCSPath(Array("--input", "gs://mosambi/terms.txt", "--algorithm","BLAS", "--output","22", "--flag","true" , "--input1", "1.4", "--float1", "1.5f", "--output1", "gs://work//output")
      ,"assertion failed: invalid value for output1, cannot be empty or just the bucket name")
  }


  ignore("argument test with gcs cloud location") {

    def fileSize(x:Int, fileName:String, isLocal:Boolean,outFile:String) = {
      object TestWithCloud2 extends GasJob[InAndOutWithCloud] {
        override def execute(t: InAndOutWithCloud)(implicit gs: GasSession): Unit = {

          val lines = Source.fromFile(t.input.inputFile).getLines().size

          lines shouldBe x
        }
      }
      if(isLocal)
      TestWithCloud2.run(InAndOutWithCloud(GCSInputLocation(fileName),0,1.0,true,1.0f,GCSOutputLocation(outFile)),isLocal)
      else {
        val list = Array("--input", fileName, "--algorithm","BLAS", "--output","22", "--flag","true" , "--input1", "1.4", "--float1", "1.5f", "--output1", "gs://work/output")
        val obj = TestWithCloud2.argumentsToObject(list)
        TestWithCloud2.run(obj,isLocal)
      }

    }
    fileSize(3,"files/terms.txt",true,"")
    fileSize(117699,"gs://mosambi/terms.txt",false,"")

  }


}

