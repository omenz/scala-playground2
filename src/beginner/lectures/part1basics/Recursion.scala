package beginner.lectures.part1basics

import beginner.lectures.part1basics.Functions.factorial2

import scala.annotation.tailrec

/**
  * Created by Alexander Krasovsky on 25.06.2018.
  */
object Recursion extends App {

  def factorial(n: Int): Int = {
    if (n <= 0) 1
    else {
      println("Computing factorial of " + n + " - I first need to compute factorial of " + (n - 1))
      val result = n * factorial2(n - 1)
      println("Computed factorial of " + n)
      result
    }
  }

  def anotherFactorial(n: Int): Int = {
    @tailrec
    def factHelper(x: Int, accumulator: Int): Int =
      if (x <= 1) accumulator
      else factHelper(x - 1, x * accumulator) //TAIL RECURSION - use recursive call as the LAST expression

    factHelper(n, 1)
  }

  def concatString(str: String, times: Int): String = {
    @tailrec
    def concatHelper(x: Int, accumulator: String): String = { //NOTICE THE TAIL RECURSIVE ICON IN THE INTELLIJ IDEA
      if (x <= 1) accumulator
      else concatHelper(x - 1, accumulator + str)
    }

    concatHelper(times, str)
  }

  println(concatString("Pew", 10))

}
