package test.com.recipegrace.biglibrary.core

import com.recipegrace.biglibrary.core.{ZipArchive, BaseTest}

/**
  * Created by Ferosh Jacob on 2/17/16.
  */
class UnZipFileTest extends BaseTest with ZipArchive{

  test("zip file test") {
    unZip("files/profiles.zip").nonEmpty should equal (true)
  }

}
