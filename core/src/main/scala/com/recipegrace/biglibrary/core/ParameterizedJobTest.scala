package com.recipegrace.biglibrary.core


import com.recipegrace.biglibrary.core.{CreateTemporaryFiles,ParameterizedJob, BaseTest}

/**
  * Created by fjacob on 9/25/15.
  */
trait ParameterizedJobTest extends BaseTest with CreateTemporaryFiles {


  def launch[T](paraJob: ParameterizedJob[T], args: T) = {
    paraJob.runLocal(args)

  }


}
