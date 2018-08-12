package beginner.lectures.part2oop

import beginner.excercises.{Cons, Empty => RenamedEmpty}//it is possible to rename imports

/**
  * Created by Alexander Krasovsky on 03.07.2018.
  */
object PackagingAndImports extends App {

  //taken from the package object
  sayHello
  println(SPEED_OF_LIGHT)

  val myList = new Cons[Int](1, RenamedEmpty)


  //default imports
  //java.lang - String, Object, Exception
  //scala - Int, Nothing, Function
  //scala.Predef - println, ???
}
