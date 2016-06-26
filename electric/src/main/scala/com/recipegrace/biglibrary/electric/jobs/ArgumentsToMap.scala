package com.recipegrace.biglibrary.electric.jobs

import java.lang

import com.recipegrace.biglibrary.electric.{ElectricContext, ElectricJob, FileAccess}

/**
  * Created by Ferosh Jacob on 10/16/15.
  */



trait ArgumentsToMap {


  private def convertArgsToMap(args: Array[String]) = {
    args.length match {
      case 0 => Map():Map[String,String]
      case _ => {
        assert(args.grouped(2).forall(f => f(0).startsWith("--")), "Unable to parse arguments:" + args.mkString(" "))
        args.grouped(2).map(f => (f(0).split("--")(1), f(1))).toMap
      }
    }
  }



  private def toArgument(parameterName:String, parameterType:String,argumentValues:Map[String,String]) = {

    def getArgumentValues(argumentName:String)= {
      argumentValues.get(argumentName) match {
        case Some(x) => x
        case _ => {
          assert(false, "missing value for argument:" + argumentName)
          ""
        }
      }
    }
    assert(!parameterName.startsWith("$"), "the Argument class should be declared at package scope, not an inner scope")
    parameterType match {
      case "java.lang.Integer" | "int" => new Integer(getArgumentValues(parameterName).toInt)
      case "java.lang.String"  => getArgumentValues(parameterName)
      case "java.lang.Double"|"double" => new java.lang.Double(getArgumentValues(parameterName).toDouble)
      case "java.lang.Float" | "float"  => new java.lang.Float(getArgumentValues(parameterName).toFloat)
      case "java.lang.Boolean" | "boolean" => new java.lang.Boolean( getArgumentValues(parameterName).toBoolean)
      case _ => {
        assert(false, "Only supported primitive types, found non-primitive type for " + parameterName)
        ""
      }
    }
  }
  def convertArgsToArgs(args: Array[String], parameters:Array[(String,String)]) :Array[Object]= {
    val map = convertArgsToMap(args)
    for( (parameterName, parameterType) <- parameters)
      yield  toArgument(parameterName,parameterType,map)
  }

  def validateArgs(inArgs:Array[String],map: Map[String, String], mainText: String, args: String*) = {

    val resultText =mainText+ ", but " + map
    require(inArgs.length == 2*map.keys.size, resultText)
    args.foreach(f => {
      require(map.contains(f),resultText )
    })
  }
}






