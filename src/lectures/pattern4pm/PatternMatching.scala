package lectures.pattern4pm

import scala.util.Random

object PatternMatching extends App {


  val random = new Random()
  val x = random.nextInt(10)

  val description = x match {
    case 1 => "the one"
    case 2 => "double or nothing"
    case 3 => "third time is a charm"
    case _ => "nothing else" // _ - wildcard
  }

  println(x)
  println(description)

  // 1. Decompose values
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 20)

  val greeting = bob match {
    case Person(n, a) if a < 21 => s"Hi, my name is $n and I can't drink in US"
    case Person(n, a) => s"Hi, my name is $n and I am $a years old"
    case _ => "I don't know who I am"
  }
  println(greeting)

  /*
  * 1. cases are matched in order
  * 2. what if no case match? -> MatchError
  * 3. type of the PM expression? -> unification of all the case return types
  * 4. PM works really well with case classes
  * */

  // PM on sealed hierarchies
  sealed class Animal //you get a compiler warning for sealed classes if not all subtypes are matched in a pattern matching
  case class Dog(breed: String) extends Animal
  case class Parrot(greeting: String) extends Animal

  val animal: Animal = Dog("Terra Nova")
  animal match {
    case Dog(someBreed) => println(s"Matched a dog of the $someBreed breed")
  }

  /*
   Excercise
  */


  trait Expr
  case class Number(n: Int) extends Expr
  case class Sum(e1: Expr, e2: Expr) extends Expr
  case class Prod(e1: Expr, e2: Expr) extends Expr

  def show(n: Expr): String = n match {
    case Number(n) => s"$n"
    case Sum(e1, e2) => s"${show(e1)} + ${show(e2)}"
    case Prod(e1, e2) => {
      def maybeShowParentheses(exp: Expr) = exp match {
        case Prod(_, _) => show(exp)
        case Number(_) => show(exp)
        case _ => s"(${show(exp)})"
      }
      s"${maybeShowParentheses(e1)} * ${maybeShowParentheses(e2)}"
    }
  }

  println(show(Sum(Number(2), Number(3))))
  println(show(Sum(Sum(Number(2), Number(3)), Number(7))))
  println(show(Prod(Sum(Number(2), Number(1)), Number(3))))
  println(show(Sum(Prod(Number(2), Number(1)), Number(3))))
}
