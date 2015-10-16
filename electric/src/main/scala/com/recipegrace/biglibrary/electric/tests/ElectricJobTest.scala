package com.recipegrace.biglibrary.electric.tests

import com.recipegrace.biglibrary.core.BaseTest
import com.recipegrace.biglibrary.electric.Launcher

import com.recipegrace.biglibrary.electric.jobs._

/**
 * Created by fjacob on 6/1/15.
 */
trait ElectricJobTest[T] extends BaseTest with Launcher[T] {


}
trait SimpleJobTest extends ElectricJobTest[TwoArgument] {}
trait OutputOnlyJobTest extends ElectricJobTest[OneArgument] {}
trait TwoInputJobTest extends ElectricJobTest[ThreeArgument]{}



