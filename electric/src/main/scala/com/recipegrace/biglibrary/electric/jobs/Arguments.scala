package com.recipegrace.biglibrary.electric.jobs

/**
  * Created by Ferosh Jacob on 1/20/16.
  */
object Arguments {

  case class OneArgument(output: String)

  case class TwoArgument(input: String, output: String)

  case class ThreeArgument(one: String, two: String, output: String)

}

