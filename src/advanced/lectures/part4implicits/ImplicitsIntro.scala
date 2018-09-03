package advanced.lectures.part4implicits

object ImplicitsIntro extends App {

  val pair = "Daniel" -> "555"
  val intPair = 1 -> 2

  case class Person(name: String) {
    def greet = s"Hi, my name is $name!"
  }

  implicit def fromStringToPerson(str: String): Person = Person(str)

  println("Peter".greet) // println(fromStringToPerson("Peter").greet)

/*  class A {
    def greet: Int = 2
  }

  implicit def fromStringToA(str: String): A = new A //will fail to compile because of 2 implicits in scope
  */

  //implicit paramters
  def increment(x: Int)(implicit amount: Int) = x + amount
  implicit val defaultAmount = 10

  increment(2)
  // NOT default args

  
}