package com.recipegrace.biglibrary.electric.jobs

import com.recipegrace.biglibrary.electric.{ElectricContext, ElectricJob, SequenceFileAccess}

/**
  * Created by Ferosh Jacob on 10/16/15.
  */

trait SequenceFileJob[T] extends ElectricJob[T] with SequenceFileAccess {

}

trait ArgumentsToMap {
  def convertArgsToMap(args: Array[String]) = {
    args.grouped(2).map(f => (f(0).split("--")(1), f(1))).toMap
  }

  def validateArgs(map: Map[String, String], mainText: String, args: String*) = {

    require(args.length == 2 * map.keys.size, mainText)
    args.foreach(f => {
      require(map.contains(f), mainText)
    })
  }
}

trait SimpleJob extends SequenceFileJob[TwoArgument] with ArgumentsToMap {

  override def execute(args: TwoArgument)(implicit ec: ElectricContext) = {
    execute(args.input, args.output)(ec)


  }

  override def parse(args: Array[String]): TwoArgument = {
    val mainText = "Should have --input val --output val"

    val mapArgs = convertArgsToMap(args)
    validateArgs(mapArgs, "input", "output")

    TwoArgument(mapArgs("input"), mapArgs("output"))
  }

  def execute(input: String, output: String)(implicit ec: ElectricContext)


}

trait OutputOnlyJob extends SequenceFileJob[OneArgument] with ArgumentsToMap {

  override def execute(args: OneArgument)(implicit ec: ElectricContext) = {
    execute(args.output)
  }

  override def parse(args: Array[String]): TwoArgument = {

    val mainText = "Should have --output val"

    val mapArgs = convertArgsToMap(args)
    validateArgs(mapArgs, "output")

    TwoArgument(mapArgs("input"), mapArgs("output"))
  }

  def execute(output: String)(implicit ec: ElectricContext)


}

trait TwoInputJob extends SequenceFileJob[ThreeArgument] with ArgumentsToMap {

  override def execute(args: ThreeArgument)(implicit ec: ElectricContext) = {
    execute(args.one, args.two, args.output)
  }

  override def parse(args: Array[String]): ThreeArgument = {
    val mainText = "Should have --input val --output val"

    val mapArgs = convertArgsToMap(args)
    validateArgs(mapArgs, "input", "output")

    ThreeArgument(mapArgs("one"), mapArgs("two"), mapArgs("output"))


  }

  def execute(one: String, two: String, output: String)(implicit ec: ElectricContext)


}

