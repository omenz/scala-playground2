package advanced.lectures.part2afp

object CurriesPAF extends App {

   // curried functions
  val superAdder: Int => Int => Int =
    x => y => x + y

  val add3 = superAdder(3) // Imt => Int = y => 3 + y

  println(add3(5))
  println(superAdder(3)(5)) // curried function

  // METHOD!
  def curriedAdder(x: Int)(y: Int): Int = x + y // curried method

  val add4: Int => Int = curriedAdder(4)

  // lifting = ETA-EXPANSION

  // functions != methods (JVM limitation)
  def inc(x: Int) = x + 1
  List(1,2,3).map(inc) // compiler does ETA-expansion and turns inc method in to a function x => inc(x)


  // Partial function applications
  val add5 = curriedAdder(5) _ // Int => Int

  // EXERCISE
  val simpleAddFunction = (x: Int, y: Int) => x + y
  def simpleAddMethod(x: Int, y: Int) = x + y
  def curriedAddMethod(x: Int)(y: Int) = x + y


  // add7: Int => Int = y => 7 + y

  val add7: Int => Int = x => simpleAddFunction(7, x)
  val add7v2: Int => Int = x => simpleAddMethod(7, x)
  val add7v3: Int => Int = curriedAddMethod(7)
  val add7v4 = simpleAddFunction.curried(7) // functions have curried method
  val add7v5 = curriedAddMethod(7) _ // PAF
  val add7v7 = curriedAddMethod(7)(_) // PAF alternative syntax

  val add7v8 = simpleAddMethod(7, _: Int) // alternative syntax for turning methods into function values
  val add7v9 = simpleAddFunction(7, _: Int) // works as well
              // y => simpleAddMethod(7, y)

  // underscores are powerful
  def concatenator(a: String, b: String, c: String) = a + b + c
  val insertName = concatenator("Hello, I'm ", _: String, ", how are you?")
  println(insertName("Daniel"))

  val fillInTheBlanks = concatenator("Hello, ", _: String, _: String) // (x, y) => concatenator("Hello, ", x, y)
  println(fillInTheBlanks("Daniel", "Scala is awesome!"))

  //EXERCISES
  /*
    1. Process a list of numbers and return their string representations with different formats
      Use the %4.2f, %8.6f and %14.12f with a curried formatter function.
  */

  val numbers = List(1.434343, 2.434322, 3.65656234234, 4.3434343434)
  val formatter: String => Double => String = x => y => x.format(y)

  val formatter4_2 = formatter("%4.2f")
  val formatter8_6 = formatter("%8.6f")
  val formatter14_12 = formatter("%14.12f")

  val formatterAll: Double => String = x => s"${formatter4_2(x)} ${formatter8_6(x)} ${formatter14_12(x)}"

  numbers.foreach(x => println(formatterAll(x)))

  /*
    2. difference between
        - functions vs methods
        - parameters: by-name vs 0-lambda
   */

  def byName(n: Int) = n + 1
  def byFunction(f: () => Int) = f() + 1

  def method: Int = 42
  def parenMethod(): Int = 42

  /*
    calling byName and byFunction
    - int
    - method
    - parenMethod
    - lambda
    - PAF
   */

  byName(42)
  byName(method)
  byName(parenMethod())
  byName(parenMethod) // of but beware ==> byName(parenMethod())
//  byName(() => 42) not ok
  byName((() => 42)())
//  byName(parenMethod _) // not ok

  byFunction(parenMethod) // compiler does ETA-expansion
  byFunction(()=> 46)
  byFunction(parenMethod _)// also works, _ is unnecessary

}
