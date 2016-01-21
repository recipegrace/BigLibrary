package test.com.recipegrace.biglibrary.core

import com.recipegrace.biglibrary.core.{TrueFalseNeutral, BaseTest}

/**
  * Created by Ferosh Jacob on 1/20/16.
  */
class TrueFalseNeutralTest extends BaseTest{


  test("check for neutral") {
    TrueFalseNeutral.False should not equal TrueFalseNeutral.True
    TrueFalseNeutral.Neutral should not equal TrueFalseNeutral.True
  }

}
