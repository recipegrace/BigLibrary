package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.tests.ElectricJobTest
import com.recipegrace.biglibrary.electric.{ElectricJob, ElectricSession}
import com.recipegrace.biglibrary.electricexamples.sanitycheck.SanityClass


/**
  * Created by fjacob on 9/25/15.
  */
case class InAndOut(input:String, output:Int, input1:Double, flag:Boolean, float1:Float)
case class NotPrimitiveArgument(inAndOut: InAndOut, sanityClass: SanityClass )
case class NotPrimitiveArgument1(threshold:Float, other:String ,inAndOut: InAndOut, sanityClass: SanityClass )
case class NotSupportedCompoundType(x:NotPrimitiveArgument)
class SparkJobArgumentTest extends ElectricJobTest {


  test("argument test") {
    val list = Array("--input", "bob", "--output","22", "--flag","true" , "--input1", "1.4", "--float1", "1.5f")
     object Test1 extends ElectricJob[InAndOut]{
      override def execute(t: InAndOut)(implicit ec: ElectricSession): Unit = {}
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
      override def execute(t: String)(implicit ec: ElectricSession): Unit = {}
    }
    intercept[AssertionError]{
      val obj = Test1.argumentsToObject(list)
    }
  }


  test("inner class test") {
    val list = Array("--input", "bob")
    case class NoInput(x:String)
    object Test1 extends ElectricJob[NoInput]{
      override def execute(t: NoInput)(implicit ec: ElectricSession): Unit = {}
    }
    intercept[AssertionError]{
      val obj = Test1.argumentsToObject(list)
    }
  }

  test("complex argument test") {
    val list = Array("--input", "bob")
    case class NoInput(x:String)
    object Test1 extends ElectricJob[NotSupportedCompoundType]{
      override def execute(t: NotSupportedCompoundType)(implicit ec: ElectricSession): Unit = {}
    }
    intercept[AssertionError]{
      val obj = Test1.argumentsToObject(list)
    }
  }
  test("sanity class test") {
    val list = Array("--x", "bob")
    object Test1 extends ElectricJob[SanityClass]{
      override def execute(t: SanityClass)(implicit ec: ElectricSession): Unit = {}
    }
    Test1.argumentsToObject(list).getX shouldBe "bob"
  }
  test("missing value test") {
    val list:Array[String] = Array()
    object Test1 extends ElectricJob[SanityClass]{
      override def execute(t: SanityClass)(implicit ec: ElectricSession): Unit = {}
    }
    intercept[AssertionError] {
      Test1.argumentsToObject(list).getX shouldBe "bob"
    }
  }

  test("sanity class argument not primitive test") {
    val list = Array("--inAndOut.input", "bob", "--inAndOut.output","22",
      "--inAndOut.flag","true" , "--inAndOut.input1", "1.4",
      "--inAndOut.float1", "1.5f","--sanityClass.x", "sam" )
    object Test1 extends ElectricJob[NotPrimitiveArgument]{
      override def execute(t: NotPrimitiveArgument)(implicit ec: ElectricSession): Unit = {}
    }
    Test1.argumentsToObject(list).inAndOut.input shouldBe "bob"
    Test1.argumentsToObject(list).sanityClass.getX shouldBe "sam"

  }
  test("sanity class argument not primitive test 2") {
    val list = Array("--inAndOut.input", "bob", "--inAndOut.output","22",
      "--inAndOut.flag","true" , "--inAndOut.input1", "1.4",
      "--inAndOut.float1", "1.5f","--sanityClass.x", "sam", "--threshold", "1.5", "--other", "this is cool" )
    object Test1 extends ElectricJob[NotPrimitiveArgument1]{
      override def execute(t: NotPrimitiveArgument1)(implicit ec: ElectricSession): Unit = {}
    }
    val test1 = Test1.argumentsToObject(list)

    test1.inAndOut.input shouldBe "bob"
    test1.sanityClass.getX shouldBe "sam"
    test1.threshold shouldBe 1.5f
    test1.other shouldBe "this is cool"
  }
}

