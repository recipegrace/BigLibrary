package com.recipegrace.biglibrary.core


/**
  * Created by Ferosh Jacob on 1/20/16.
  */
class TrueFalseNeutralTest extends BaseTest  {


  test("check for neutral") {
    False should not equal True
    Neutral should not equal True
  }

}
