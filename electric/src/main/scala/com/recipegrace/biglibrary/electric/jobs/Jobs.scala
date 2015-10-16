package com.recipegrace.biglibrary.electric.jobs

import com.recipegrace.biglibrary.core.Mappable
import com.recipegrace.biglibrary.electric.{SequenceFileAccess, ElectricContext, ElectricJob}

/**
 * Created by Ferosh Jacob on 10/16/15.
 */

trait SequenceFileJob[T] extends ElectricJob[T] with SequenceFileAccess

trait SimpleJob extends SequenceFileJob[TwoArgument]  {
  override val argumentType = implicitly[Mappable[TwoArgument]]

  override def job(args:TwoArgument)(implicit ec: ElectricContext) = {
    execute(args.input, args.output)(ec)


  }
   def execute(input:String, output:String)(ec:ElectricContext)


}
trait OutputOnlyJob extends SequenceFileJob[OneArgument] {
  override val argumentType = implicitly[Mappable[OneArgument]]

  override def job(args:OneArgument) (implicit ec: ElectricContext)= {
    execute(args.output)
  }
   def execute(output:String)(implicit ec:ElectricContext)


}
trait TwoInputJob extends SequenceFileJob[ThreeArgument] {
  override val argumentType = implicitly[Mappable[ThreeArgument]]

  override def job(args:ThreeArgument) (implicit ec: ElectricContext)= {
    execute(args.one, args.two, args.output)
  }
  def execute(one:String, two:String, output:String)(implicit ec:ElectricContext)


}

