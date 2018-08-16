package advanced.lectures.part2afp

import scala.util.Try

object PartialFunctions extends App {

  val aFunction = (x: Int) => x + 1 // Function1[Int, Int] === Int => Int

  val aFussyFunction = (x: Int) =>
    if (x == 1) 42
    else if (x == 2) 56
    else if (x == 5) 999
    else throw new FunctionNotApplicableException

  class FunctionNotApplicableException extends RuntimeException

  val aNicerFussyFunction = (x: Int) => x match {
    case 1 => 42
    case 2 => 56
    case 3 => 999
  }
  // {1,2,5} => Int
  
  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 56
    case 3 => 999
  } // partial function value

  println(aPartialFunction(2))

  // PF utilities
  println(aPartialFunction.isDefinedAt(67))

  //lift
  val lifted = aPartialFunction.lift // Int => Option[Int]
  println(lifted(2))
  println(lifted(98)) // None

  val pfChain = aPartialFunction.orElse[Int, Int] {
    case 45 => 67
  }

  println(pfChain(2))
  println(pfChain(45))

  //PF extend normal functions

  val aTotalFunction: Int => Int = {
    case 1 => 99
  }

  // HOFs accept partial functions as well
  val aMappedList = List(1,2,3).map {
    case 1 => 42
    case 2 => 78
    case 3 => 1000
  }
  println(aMappedList)

  /*
    Note: PF can only have ONE parameter type
   */

  /**
    * Excercises
    * 1. - construct a PF instance yourself (anonymous class)
    * 2. - chatbot as a PF
    */

  val aFussyFunctionAnonymous = new PartialFunction[Int, Int] {
    override def isDefinedAt(x: Int): Boolean = Try(apply(x)).isSuccess

    override def apply(x: Int): Int = x match {
      case 1 => 42
      case 2 => 56
      case 3 => 999
    }
  }

  scala.io.Source.stdin.getLines().foreach {
    case "" => println("You said nothing")
    case "Hello!" => println("Hello there General Kenobi")
    case str: String => println(s"You said: $str ")
  }
}
