package advanced.lectures.part2afp

object LazyEvaluation extends App {


  // lazy DELAYS the evaluation of values
  lazy val x: Int = {
    println("hello")
    42
  }
  println(x)
  println(x)

  // example of implications:
  def sideEffectCondition: Boolean = {
    println("Boo")
    true
  }
  def simpleCondition: Boolean = false

  lazy val lazyCondition = sideEffectCondition
  println(if (simpleCondition && lazyCondition) "yes" else "no") // println Boo never happens

  // in conjunction with call by name
  def byNameMethod(n: => Int): Int = {
    lazy val t = n // only evaluated once
    t + t + t + 1
  }
  def retrieveMagicValue: Int = {
    println("waiting")
    Thread.sleep(1000)
    42
  }

  println(byNameMethod(retrieveMagicValue))
  // use lazy vals


  //filtering with lazy vals
  def lessThan30(i: Int): Boolean = {
    println(s"$i is less than 30?")
    i < 30
  }
  def greaterThan20(i: Int): Boolean = {
    println(s"$i is greater than 20?")
    i > 20
  }

  val numbers = List(1, 25, 40, 5, 23)
  val lt30 = numbers.filter(lessThan30) // List(1, 25, 5, 23)
  val gt20 = lt30.filter(greaterThan20) // List(25, 23)
  println(gt20)

  val lt30lazy = numbers.withFilter(lessThan30) //lazy vals under the hood
  val gt20lazy = lt30lazy.withFilter(greaterThan20)
  println("lazy filtering")
  println(gt20lazy) //scala.collection.TraversableLike$WithFilter@799d4f69
  //to really print we need to do
  gt20lazy.foreach(println)

  // for-comprehensions use withFilter with guards
  for {
    a <- List(1,2,3) if a % 2 == 0 // use lazy vals!
  } yield a + 1
  //same as
  List(1,2,3).withFilter(_ % 2 == 0).map(_ + 1) //List[Int]
}
