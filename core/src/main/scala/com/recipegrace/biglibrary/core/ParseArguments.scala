package com.recipegrace.biglibrary.core

import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory


/**
 * Created by FXJ6302 on 9/25/2015.
 */

case class Argument(name:String, isRequired:Boolean=true, default:String="")

object ParseArguments {


  def parse[T: Mappable](args: Array[String]): Option[T] = {
    try {
      Some(implicitly[Mappable[T]].fromList(stringToAny(args)))
    } catch {
      case x: Throwable => {
        val logger = Logger(LoggerFactory.getLogger("Parse arguments"))
        logger.error(x.getMessage)
        None
      }
    }
  }


    def stringToAny[T](args:Array[String]):Array[Any] = {
      args.grouped(2).map(f=> (f(0).split("--",-1)(1),toAny(f(1)))).map(f=> List(f._1,f._2)).flatten.toArray
    }

    def toAny(x:String) = {
      x.trim.toUpperCase() match {

        case "TRUE" =>  true
        case "FALSE" => false
        case _ => {
          if(x.forall(_.isDigit)) x.toInt
          else if(x.matches("[+-]?\\d+.?\\d+")	) x.toDouble
          else x
        }
      }
    }
  }







