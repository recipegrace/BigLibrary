package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.tests.ElectricJobTest
import com.recipegrace.biglibrary.electric.{ElectricJob, ElectricContext}
import com.recipegrace.biglibrary.electricexamples.sanitycheck.SanityClass


/**
  * Created by fjacob on 9/25/15.
  */
case class InAndOut(input:String, output:Int, input1:Double, flag:Boolean, float1:Float)

class SparkJobArgumentTest extends ElectricJobTest {


  test("argument test") {
    val list = Array("--input", "bob", "--output","22", "--flag","true" , "--input1", "1.4", "--float1", "1.5f")
     object Test1 extends ElectricJob[InAndOut]{
      override def execute(t: InAndOut)(implicit ec: ElectricContext): Unit = {}
    }
    val obj = Test1.argumentsToObject(list)
    obj.input shouldBe "bob"
    obj.output shouldBe 22
    obj.input1 shouldBe 1.4
    obj.flag shouldBe true
    obj.float1 shouldBe 1.5f
    intercept[AssertionError]{
      val list = Array("--input", "bob", "--output","22", "--flag","true" , "--input1", "1.4", "float1", "1.5f")
      val obj = Test1.argumentsToObject(list)
    }
  }


  test("more than one constructor test") {
    val list = Array("--input", "bob")
    object Test1 extends ElectricJob[String]{
      override def execute(t: String)(implicit ec: ElectricContext): Unit = {}
    }
    intercept[AssertionError]{
      val obj = Test1.argumentsToObject(list)
    }
  }


  test("inner class test") {
    val list = Array("--input", "bob")
    case class NoInput
    object Test1 extends ElectricJob[NoInput]{
      override def execute(t: NoInput)(implicit ec: ElectricContext): Unit = {}
    }
    intercept[AssertionError]{
      val obj = Test1.argumentsToObject(list)
    }
  }
  test("sanity class test") {
    val list = Array("--x", "bob")
    object Test1 extends ElectricJob[SanityClass]{
      override def execute(t: SanityClass)(implicit ec: ElectricContext): Unit = {}
    }
    Test1.argumentsToObject(list).getX shouldBe "bob"
  }
  test("missing value test") {
    val list:Array[String] = Array()
    object Test1 extends ElectricJob[SanityClass]{
      override def execute(t: SanityClass)(implicit ec: ElectricContext): Unit = {}
    }
    intercept[AssertionError] {
      Test1.argumentsToObject(list).getX shouldBe "bob"
    }
  }
}

