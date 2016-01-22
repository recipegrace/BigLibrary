package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.{ElectricContext, FileAccess}

/**
  * Created by Ferosh Jacob on 11/3/15.
  */
trait Parser extends FileAccess {

  def inputParse(input: String)(implicit ec: ElectricContext) = {

    readFile(input)
      .filter(f => f.split("\\t", -1).size == 4)
      .map(f => {
        val parts = f.split("\\t", -1)
        (parts(1), parts(2))
      })


      .filter(f => f._1.trim.length > 0 && f._2.forall(_.isDigit))
      .map(f => (f._1, f._2.toLong))
  }

}
