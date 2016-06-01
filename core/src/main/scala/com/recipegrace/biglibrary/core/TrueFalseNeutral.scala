package com.recipegrace.biglibrary.core

/**
  * Created by Ferosh Jacob on 1/20/16.
  */
sealed  trait  TrueFalseNeutral

object True extends TrueFalseNeutral
object False extends  TrueFalseNeutral
object Neutral extends TrueFalseNeutral
