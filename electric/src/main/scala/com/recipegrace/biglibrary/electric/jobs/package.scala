package com.recipegrace.biglibrary.electric

/**
 * Created by Ferosh Jacob on 10/16/15.
 */
package object jobs {

  //Most jobs have output, so if there is only argument it should be output
  case class  OneArgument(output:String)
  case class  TwoArgument(input:String,output:String)
  case class  ThreeArgument(one:String, two:String, output:String)

}
