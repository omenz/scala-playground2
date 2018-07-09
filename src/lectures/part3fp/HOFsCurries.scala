package lectures.part3fp

/**
  * Created by Alexander Krasovsky on 09.07.2018.
  */
object HOFsCurries extends App {

  //map, flatMap, filter in MyList are higher order functions, they take a function as a parameter

  //function that applies a function n times over a value x
  //nTimes(f, n, x)

  def nTimes(f: Int => Int, n: Int, x: Int): Int =
    if (n <= 0) x
    else nTimes(f, n - 1, f(x))

  println(nTimes(_ + 1, 10, 1))

  def nTimesBetter(f: Int => Int, n: Int): Int => Int =
    if (n <= 0) x => x
    else x => nTimesBetter(f, n - 1)(f(x))

  val plusOne: Int => Int = _ + 1
  val plusTen = nTimesBetter(plusOne, 10)

  println(plusTen(2))

  //curried functions
  val superAdder: Int => (Int => Int) = (x: Int) => (y: Int) => x + y
  val add3 = superAdder(3) //y = 3 + y
  println(add3(18))

  //functions with multiple parameter lists
  def curriedFormatter(c: String)(x: Double): String = c.format(x)

  val stdFormat: Double => String = curriedFormatter("%4.2f")
  val preciseFormat: Double => String = curriedFormatter("%10.8f")

  println(preciseFormat(Math.PI))
}
