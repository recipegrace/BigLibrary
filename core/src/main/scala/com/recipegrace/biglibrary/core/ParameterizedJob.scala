package com.recipegrace.biglibrary.core

import com.thoughtworks.paranamer.AdaptiveParanamer

import scala.reflect.ClassTag

abstract class ParameterizedJob[T: ClassTag] extends ArgsToMap {

  def main(args: Array[String]) = {
    run(args, false)
  }

  def argumentsToObject(args: Array[String], isLocal: Boolean = false): T = {
    import scala.reflect._
    val clazz = classTag[T].runtimeClass
    val paranamer = new AdaptiveParanamer()
    val constructors = clazz.getConstructors()
    assert(
      constructors.size == 1,
      "only one contructor allowed for input argument " + clazz
    )
    val constructor = constructors.head
    val types = constructor.getParameterTypes.map(f => f.getTypeName)
    val names = paranamer.lookupParameterNames(constructor)
    val arguments = convertArgsToArgs(args, names.zip(types), isLocal)
    val instance = constructor.newInstance(arguments: _*).asInstanceOf[T]
    instance
  }

  def run(args: Array[String], isLocal: Boolean): Unit = {

    val instance: T = argumentsToObject(args, isLocal)
    run(instance, isLocal)
  }

  def run(args: T, isLocal: Boolean): Unit

  def jobName: String = this.getClass.getName

  def runLocal(args: T) = {
    run(args, true)
  }

}
