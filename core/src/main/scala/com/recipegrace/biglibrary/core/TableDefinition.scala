package com.recipegrace.biglibrary.core

/**
  * Created by fjacob on 09/25/15.
  */

abstract class TableDefinition[T] {

  def tableName: TableName

  def columns: Columns[T]


}

case class TableName(local: String, remote: String)

case class Columns[T](local: (String) => T, remote: (String) => T)

