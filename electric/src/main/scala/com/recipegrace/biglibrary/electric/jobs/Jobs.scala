package com.recipegrace.biglibrary.electric.jobs

import com.recipegrace.biglibrary.electric.{ElectricContext, ElectricJob, SequenceFileAccess}

/**
 * Created by Ferosh Jacob on 10/16/15.
 */

trait SequenceFileJob[T] extends ElectricJob[T] with SequenceFileAccess {

}
trait ArgumentsToMap {
  def convertArgsToMap(args:Array[String]) = {
    try {
      args.grouped(2).map(f=> (f(0).split("--")(1), f(1))).toMap
    }

  }
}

trait SimpleJob extends SequenceFileJob[TwoArgument] with ArgumentsToMap{

  override def job(args: TwoArgument)(implicit ec: ElectricContext) = {
    execute(args.input, args.output)(ec)


  }

  override def parse(args:Array[String]):TwoArgument = {
    require(args.length==4, "Should have --input val --output val")

    val mapArgs=convertArgsToMap(args)

    require(mapArgs.contains("input"),"Should have --input val --output val")
    require(mapArgs.contains("output"),"Should have --input val --output val")

    TwoArgument(mapArgs("input"), mapArgs("output"))
  }

  def execute(input: String, output: String)(ec: ElectricContext)


}

trait OutputOnlyJob extends SequenceFileJob[OneArgument] with ArgumentsToMap{

  override def job(args: OneArgument)(implicit ec: ElectricContext) = {
    execute(args.output)
  }

  override def parse(args:Array[String]):OneArgument = {
    require(args.length==2)
    val mapArgs=convertArgsToMap(args)

    require(mapArgs.contains("output"),"Should have --output val")

    OneArgument(mapArgs("output"))
  }
  def execute(output: String)(implicit ec: ElectricContext)


}

trait TwoInputJob extends SequenceFileJob[ThreeArgument] with ArgumentsToMap {

  override def job(args: ThreeArgument)(implicit ec: ElectricContext) = {
    execute(args.one, args.two, args.output)
  }
  override def parse(args:Array[String]):ThreeArgument = {
    require(args.length==6, "Should have --one val --two val --output val")

    val mapArgs=convertArgsToMap(args)

    require(mapArgs.contains("one"),"Should have --one val --two val --output val")
    require(mapArgs.contains("two"),"Should have --one val --two val --output val")
    require(mapArgs.contains("output"),"Should have --one val --two val --output val")

    ThreeArgument(mapArgs("one"), mapArgs("two"), mapArgs("output"))
  }
  def execute(one: String, two: String, output: String)(implicit ec: ElectricContext)


}

