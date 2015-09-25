package com.recipegrace.biglibrary.core

import joptsimple.OptionParser

/**
 * Created by FXJ6302 on 9/25/2015.
 */

case class Argument(name:String, isRequired:Boolean=true, default:String="")
trait ParseArguments {

  def parse(args:Array[String], names:Set[Argument]): Map[Argument, String] = {
    val parser = new OptionParser()
    for(name<- names){
      if(name.isRequired) parser.accepts(name.name).withRequiredArg().defaultsTo(name.default)
      else parser.accepts(name.name).withOptionalArg().defaultsTo(name.default)
    }

    val parsed= parser.parse(args:_*)
    names.map(f=> (f, parsed.valueOf(f.name).toString)).toMap

  }




}
