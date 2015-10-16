package com.recipegrace.biglibrary.core

/**
 * Created by Ferosh Jacob on 10/11/15.
 */

import scala.language.experimental.macros
import scala.reflect.macros.whitebox.Context

trait Mappable[T] {
  def fromList(args: Array[Any]): T


}

object Mappable {
  implicit def materializeMappable[T]: Mappable[T] = macro materializeMappableImpl[T]

  def materializeMappableImpl[T: c.WeakTypeTag](c: Context): c.Expr[Mappable[T]] = {
    import c.universe._
    val tpe = weakTypeOf[T]
    val companion = tpe.typeSymbol.companion

    val fields = tpe.decls.collectFirst {
      case m: MethodSymbol if m.isPrimaryConstructor ⇒ m
    }.get.paramLists.head


    val fromListParams = fields.map { field =>
      val name = field.name
      val decoded = name.decodedName.toString
      val returnType = tpe.decl(name).typeSignature
      q"""args.grouped(2).map(f=> (f(0),f(1))).toMap.get($decoded).get.asInstanceOf[$returnType]"""
    }

    c.Expr[Mappable[T]] { q"""
      new Mappable[$tpe] {
        def fromList(args: Array[Any]): $tpe = $companion(..$fromListParams)
      }
    """ }


  }
}
