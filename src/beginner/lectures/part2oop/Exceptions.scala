package beginner.lectures.part2oop

/**
  * Created by Alexander Krasovsky on 03.07.2018.
  */
object Exceptions extends App {
//  val aWeirdValue = throw new NullPointerException// aWeirdValue is of type Nothing

  def getInt(withExceptions: Boolean): Int = {
    if (withExceptions) throw new RuntimeException("No Int for you :(")
    else 34
  }

  try {
    getInt(true)
  } catch {
    case e: RuntimeException => println("It is a runtime exception " + e)
  } finally {
    println("Finally!")
  }


  //basically exceptions are the same as in java
}
