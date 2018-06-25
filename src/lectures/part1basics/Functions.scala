package lectures.part1basics

/**
  * Created by Alexander Krasovsky on 24.06.2018.
  */
object Functions extends App {

  def aFunction(a: Int, b: String): String = {
    a + " " + b
  }

  println(aFunction(1, "pew"))


  def aRepeatedFunction(aString: String, n: Int): String = {
    if (n == 1) aString
    else aString + aRepeatedFunction(aString, n -1)
  }

  //WHEN YOU NEED LOOPS, USE RECURSION

  def aBigFunction(n: Int): Int = {
    def aSmallerFunction(a: Int, b: Int): Int = a + b
    aSmallerFunction(n, n -1)
  }

  //1 greeting function
  def aGreetingFunction(name: String, age: Int): Unit = {
    println("Hi, my name is " + name + " and I am " + age + " years old.")
  }
  aGreetingFunction("David", 12)

  //2 factorial function 1 * 2 * 3 * ... * n
  def factorial(n: Int): Int = {
    if (n <= 0) 1
    else computeFactorialNextValue(1, 1, n)
  }

  private def computeFactorialNextValue(accumulatedValue: Int, iterationIndex: Int, stopAtIteration: Int): Int = {
    val result = accumulatedValue * iterationIndex
    if (stopAtIteration == iterationIndex) {
      result
    } else {
      val nextIteration = iterationIndex + 1
      computeFactorialNextValue(result, nextIteration, stopAtIteration)
    }
  }

  //2.1 factorial simplified
  def factorial2(n: Int): Int = {
    if (n <= 0) 1
    else n * factorial2(n - 1)
  }

  println(factorial(4))
  println(factorial2(4))

  //3 A Fibonacci Function
  def fibonacci(n: Int): Int = {
    if (n >= 0 && n <= 2) 1
    else computeNextFibonacciNumber(1, 1, 3, n)
  }

  private def computeNextFibonacciNumber(nTwoIterationsBefore: Int, nOneIterationBefore: Int, currentIteration: Int, stopAtIteration: Int): Int = {
    val result = nTwoIterationsBefore + nOneIterationBefore
    if (stopAtIteration == currentIteration) {
      result
    } else {
      val nextIteration = currentIteration + 1
      computeNextFibonacciNumber(nOneIterationBefore, result, nextIteration, stopAtIteration)
    }
  }

  //3.1 fibonacci simplified
  def fibonacci2(n: Int): Int = {
    if (n >= 0 && n <= 2) 1
    else fibonacci2(n - 2) + fibonacci2(n - 1)
  }

  println(fibonacci(2))
  println(fibonacci(3))
  println(fibonacci(8))

  println(fibonacci2(2))
  println(fibonacci2(3))
  println(fibonacci2(8))

  //Is Prime number function
  def isPrime(n: Int): Boolean = !((2 until n - 1) exists (n % _ == 0))
  println(isPrime(17))
  println(isPrime(94))
}
