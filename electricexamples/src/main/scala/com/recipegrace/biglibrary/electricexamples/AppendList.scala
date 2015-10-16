package com.recipegrace.biglibrary.electricexamples

import com.recipegrace.biglibrary.electric.ElectricContext
import com.recipegrace.biglibrary.electric.jobs.{TwoInputJob, OutputOnlyJob}

/**
  * Created by Ferosh Jacob on 10/16/15.
  */
object AppendList extends TwoInputJob{
   override def execute(one:String, two:String, output: String)(implicit ec: ElectricContext): Unit = {


     val first= readFile(one)
     val second = readFile(two)
     writeFile(first++second, output)
   }
 }
