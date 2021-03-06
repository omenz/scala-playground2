package advanced.lectures.part4implicits

object PimpMyLibrary extends App {

  // 2.isPrime

  implicit class RichInt(val value: Int) extends AnyVal { // AnyVal for memory optimization
    def isEven: Boolean = value % 2 == 0
    def sqrt: Double = Math.sqrt(value)
    def times(function: () => Unit) = {
      def timesAux(n: Int): Unit =
        if (n <= 0) ()
        else {
          function()
          timesAux(n - 1)
        }
      timesAux(value)
    }

    def *[T](list: List[T]): List[T] = {
      def concatenate(n: Int): List[T] =
        if (n <= 0) List()
        else concatenate(n - 1) ++ list

      concatenate(value)
    }
  }

  implicit class RicherInt(richInt: RichInt) {
    def isOdd: Boolean = richInt.value % 2 != 0
  }

  new RichInt(42).sqrt

  42.sqrt
  42.isEven
  //type enrichment = pimping

  1 to 10 // also type enrichment

  import scala.concurrent.duration._
  2.seconds
  

//  42.isOdd   // can't wrap

  /*
    Enrich the String class
    - asInt
    - encrypt
      "John" -> Lqjp

     Keep enriching the Int class
     - times(function)
     3.times(() => ....)
     - *
      3 * List(1,2) => List(1,2,1,2,1,2)
   */

  implicit class RichString(string: String) {
    def asInt: Int = Integer.valueOf(string)// java.lang.Integer -> Int
    def encrypt(cypherDistance: Int): String = string.map(c => (c + cypherDistance).asInstanceOf[Char])
  }
  
  println("3".asInt + 4)
  println("John".encrypt(2))

  3.times(() => println("Scala Rocks!"))
  println(4 * List(1,2))

  // "3" / 4 like in javascript
  implicit def stringToInt(string: String): Int = Integer.valueOf(string) //implicit conversion
  println("6" / 2)

  // equivalent: implicit class RichAltInt(value: Int)
  class RichAltInt(value: Int)
  implicit def enrich(value: Int): RichAltInt = new RichAltInt(value)

  // danger zone
  implicit def IntToBoolean(i: Int): Boolean = i == 1

  /*
    if (n) do something
    else do something else
   */
  val aConditionedValue = if (3) "OK" else "Something wrong"
  println(aConditionedValue)
}
