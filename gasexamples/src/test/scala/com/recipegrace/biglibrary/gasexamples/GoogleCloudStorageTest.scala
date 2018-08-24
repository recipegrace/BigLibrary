package com.recipegrace.biglibrary.gasexamples

import java.io.File

import com.recipegrace.biglibrary.gas.tests.GasJobTest
import com.recipegrace.biglibrary.gas.{GCSInputLocation, GasJob, GasSession}
import com.recipegrace.biglibrary.gas.gcloud.GoogleCloudStorage
import java.nio.file.{Files, Paths}
import scala.collection.JavaConverters._
/**
  * Created by fjacob on 9/25/15.
  */
class GoogleCloudStorageTest extends GasJobTest {

    ignore("google cloud download test file"){
        val output = createTempPath
        GoogleCloudStorage.download("mosambi", "terms.txt", Paths.get(output))
        scala.io.Source.fromFile(output).getLines.size shouldBe 117699

    }

    ignore("google cloud download test folder"){
        val output = createTempPath
        GoogleCloudStorage.download("mosambi", "work/davinci", Paths.get(output))
        Files.list(Paths.get(output)).count() shouldBe 2
    }
    ignore("google cloud upload test folder"){
        val output = createTempPath
        val message = "this is awesome"
        GoogleCloudStorage.uploadFile("mosambi", "work/aweoms.txt",message.getBytes())
        GoogleCloudStorage.download("mosambi", "work/aweoms.txt", Paths.get(output))
        scala.io.Source.fromFile(output).getLines.toList.head shouldBe message
    }
}