package com.recipegrace.biglibrary.core

import com.thoughtworks.paranamer.AdaptiveParanamer

/**
  * Created by Ferosh Jacob on 10/16/15.
  */

trait ArgsToMap {


  private def convertArgsToMap(args: Array[String]) = {
    args.length match {
      case 0 => Map():Map[String,String]
      case _ => {
        assert(args.grouped(2).forall(f => f(0).startsWith("--")), "Unable to parse arguments:" + args.mkString(" "))
        args.grouped(2).map(f => (f(0).split("--")(1), f(1))).toMap
      }
    }
  }

  def toArgumentObject(parameterName: String, parameterType: String, argumentValues: Map[String, String]):Object ={
    val paranamer = new AdaptiveParanamer()
    val clazz = this.getClass.getClassLoader.loadClass(parameterType)
    val constructors = clazz.getConstructors
    assert(constructors.size == 1, "only one contructor allowed for input argument " + clazz)
    val constructor = constructors.head
    val types = constructor.getParameterTypes.map(f => f.getTypeName)
    val names = paranamer.lookupParameterNames(constructor)
    val arguments=for( (innerparameterName, parameterType) <- names.zip(types))
      yield  toArgument(parameterName+"."+innerparameterName,parameterType,argumentValues,true)
    val instance:Object = constructor.newInstance(arguments: _*).asInstanceOf[Object]
    instance
  }
  def getArgumentValues(argumentName:String,argumentValues:Map[String,String])= {
      argumentValues.get(argumentName) match {
        case Some(x) => x
        case _ => {
          assert(false, "missing value for argument:" + argumentName)
          ""
        }
      }
    }

  def toArgument(parameterName:String, parameterType:String, argumentValues:Map[String,String], isLocal:Boolean,isLevelOne:Boolean=false) = {
    assert(!parameterName.startsWith("$"), "the Argument class should be declared at package scope, not an inner scope")
    parameterType match {
      case "java.lang.Integer" | "int" => new Integer(getArgumentValues(parameterName,argumentValues).toInt)
      case "java.lang.String"  => getArgumentValues(parameterName,argumentValues)
      case "java.lang.Double"|"double" => new java.lang.Double(getArgumentValues(parameterName,argumentValues).toDouble)
      case "java.lang.Float" | "float"  => new java.lang.Float(getArgumentValues(parameterName,argumentValues).toFloat)
      case "java.lang.Boolean" | "boolean" => new java.lang.Boolean( getArgumentValues(parameterName,argumentValues).toBoolean)
      case x:String => {
       if(isLevelOne)
         assert(false, "Only supported one level")
        toArgumentObject(parameterName,parameterType,argumentValues)
      }
    }
  }
  def convertArgsToArgs(args: Array[String], parameters:Array[(String,String)],isLocal:Boolean) :Array[Object]= {
    val map = convertArgsToMap(args)
    for( (parameterName, parameterType) <- parameters)
      yield  toArgument(parameterName,parameterType,map,isLocal)
  }



  def validateArgs(inArgs:Array[String],map: Map[String, String], mainText: String, args: String*) = {

    val resultText =mainText+ ", but " + map
    require(inArgs.length == 2*map.keys.size, resultText)
    args.foreach(f => {
      require(map.contains(f),resultText )
    })
  }

  def convertTypesToPrimitiveTypes(types: Array[String], isFirstDegree:Boolean=false) = {
    types.flatMap {
      case f@("java.lang.Integer" | "int" | "java.lang.String" | "java.lang.Double" | "double" | "java.lang.Float" | "float" | "java.lang.Boolean" | "boolean") =>
        List(f)
      case f => {
        if(!isFirstDegree)
        assert(false,"Input argument can be one level depth")
        convertToPrimitiveType(f)
      }
    }
  }
  def convertToPrimitiveType(classType:String):Array[String]= {

    val clazz = this.getClass.getClassLoader.loadClass(classType)
    val constructors = clazz.getConstructors
    assert(constructors.size == 1, "only one contructor allowed for input argument " + clazz)
    val constructor = constructors.head
    val types = constructor.getParameterTypes.map(f => f.getTypeName)
    convertTypesToPrimitiveTypes(types,true)
  }


}
