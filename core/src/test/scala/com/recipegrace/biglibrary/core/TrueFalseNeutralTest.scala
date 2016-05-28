package com.recipegrace.biglibrary.core


/**
  * Created by Ferosh Jacob on 1/20/16.
  */
class TrueFalseNeutralTest extends BaseTest {


  test("check for neutral") {
    TrueFalseNeutral.False should not equal TrueFalseNeutral.True
    TrueFalseNeutral.Neutral should not equal TrueFalseNeutral.True
  }

}
